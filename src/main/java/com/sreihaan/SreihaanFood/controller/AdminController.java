package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import com.sreihaan.SreihaanFood.dto.OrderDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import com.sreihaan.SreihaanFood.model.requests.AdminCreateOrderRequest;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(OrderPage orderPage)
    {
        Page<Order> orderList = orderService.getAllOrdersForAdmin(orderPage);
        Page<OrderDTO> orderDTOPage = OrderDTO.convertEntityListToPage(orderList);
        return ResponseEntity.ok(orderDTOPage);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId)
    {
        Order order = orderService.getOrderByOrderIdForAdmin(orderId);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> makeOrder(@RequestBody AdminCreateOrderRequest adminCreateOrderRequest)
    {
        Hashtable<Long, Long> productIdVsQuantity = new Hashtable<>();
        for(CartItemDTO cartItemDTO : adminCreateOrderRequest.getCartItemDTOS())
        {
            long productId = cartItemDTO.getProductId();
            long quantity = cartItemDTO.getQuantity();
            productIdVsQuantity.put(productId, quantity);
        }
        String email = adminCreateOrderRequest.getEmail();
        Address address = AddressDTO.convertAddressDTOToEntity(adminCreateOrderRequest.getAddressDTO());
        Order order = orderService.makeOrderForAdmin(email, productIdVsQuantity, address);
        return null;
    }

    @PostMapping("/order/{orderId}/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable String orderId, @RequestBody Status status)
    {
        Order order = orderService.updateOrderStatus(orderId, status);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}
