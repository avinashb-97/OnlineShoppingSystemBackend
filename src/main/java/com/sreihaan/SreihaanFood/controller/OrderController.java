package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.GuestOrderDTO;
import com.sreihaan.SreihaanFood.dto.OrderDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.requests.CreateOrderRequest;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/guest")
    public void createGuestOrder(@RequestBody GuestOrderDTO guestOrderDTO)
    {
        String email = guestOrderDTO.getEmail();
        emailSenderService.sendEmail("email","Order Placed Successfully!","Your order has been placed successfully");
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> makeOrder(@RequestBody CreateOrderRequest createOrderRequest)
    {
        long addressId = createOrderRequest.getAddressId();
        Order order = orderService.makeOrderForCurrentUser(addressId);
        emailSenderService.sendOrderConfirmationMail(order);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<OrderDTO>> getAllOrders(OrderPage orderPage)
    {
        Page<Order> orderList = orderService.getAllOrdersForCurrentUser(orderPage);
        Page<OrderDTO> orderDTOPage = OrderDTO.convertEntityListToPage(orderList);
        return ResponseEntity.ok(orderDTOPage);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId)
    {
        Order order = orderService.getOrderByOrderId(orderId);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}
