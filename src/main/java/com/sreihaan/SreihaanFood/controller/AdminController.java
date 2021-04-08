package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import com.sreihaan.SreihaanFood.dto.OrderDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import com.sreihaan.SreihaanFood.model.requests.AdminCreateOrderRequest;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/order")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestParam(required = false) String orderId, @RequestParam(required = false) String email, OrderPage orderPage)
    {
        Page<OrderDTO> orderDTOS = null;
        if (orderId != null)
        {
            Order order = orderService.getOrderByOrderIdForAdmin(orderId);
            List<Order> orders = new ArrayList<>();
            orders.add(order);
            Page<Order> pages = new PageImpl<Order>(orders);
            orderDTOS = OrderDTO.convertEntityListToPage(pages);
        }
        else if(email != null)
        {
            Page<Order> orders = orderService.getAllOrdersUserByEmail(email, orderPage);
            orderDTOS = OrderDTO.convertEntityListToPage(orders);
        }
        else
        {
            Page<Order> orderList = orderService.getAllOrdersForAdmin(orderPage);
            orderDTOS = OrderDTO.convertEntityListToPage(orderList);
        }
        return ResponseEntity.ok(orderDTOS);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> makeOrder(@RequestBody AdminCreateOrderRequest adminCreateOrderRequest)
    {
        Hashtable<Long, Long> productIdVsQuantity = new Hashtable<>();
        Hashtable<Long, BigDecimal> productIdVsPrice = new Hashtable<>();
        for(CartItemDTO cartItemDTO : adminCreateOrderRequest.getCartItems())
        {
            long productId = cartItemDTO.getProductId();
            long quantity = cartItemDTO.getQuantity();
            BigDecimal unitPrice = cartItemDTO.getUnitPrice();
            productIdVsQuantity.put(productId, quantity);
            productIdVsPrice.put(productId, unitPrice);
        }
        String email = adminCreateOrderRequest.getEmail();
        Address address = AddressDTO.convertAddressDTOToEntity(adminCreateOrderRequest.getAddress());
        Order order = orderService.makeOrderForAdmin(email, address, productIdVsQuantity, productIdVsPrice);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/order/{orderId}/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable String orderId, @RequestBody Status status)
    {
        Order order = orderService.updateOrderStatus(orderId, status);
        emailSenderService.sendOrderConfirmationMail(order);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}