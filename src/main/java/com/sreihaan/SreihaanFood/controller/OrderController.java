package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.GuestOrderDTO;
import com.sreihaan.SreihaanFood.model.requests.CreateOrderRequest;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void makeOrder(@RequestBody CreateOrderRequest createOrderRequest)
    {
        long addressId = createOrderRequest.getAddressId();
        orderService.makeOrderForCurrentUser(addressId);
    }

}
