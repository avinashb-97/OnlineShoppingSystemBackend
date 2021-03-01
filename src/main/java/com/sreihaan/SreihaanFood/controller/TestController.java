package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/mail/{mailId}")
    public void sendMail(@PathVariable String mailId)
    {
        emailSenderService.sendEmail(mailId,"Test", "Hello Test 123 !");
    }

    @GetMapping("/hello")
    public String hello()
    {
        return "hello world!";
    }

}
