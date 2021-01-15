package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping
    public void sendMail() throws MessagingException {
        emailSenderService.sendEmail("avinashbb695@gmail.com","Complete Registration!","To confirm your account, please click here : ");
    }

}
