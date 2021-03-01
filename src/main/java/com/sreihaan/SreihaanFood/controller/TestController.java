package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.service.EmailSenderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/test")
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
