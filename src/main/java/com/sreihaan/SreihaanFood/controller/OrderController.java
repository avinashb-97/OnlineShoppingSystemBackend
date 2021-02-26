package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.GuestOrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order")
@RestController
public class OrderController {

    @PostMapping("/guest")
    public void createGuestOrder(@RequestBody GuestOrderDTO guestOrderDTO)
    {

    }

}
