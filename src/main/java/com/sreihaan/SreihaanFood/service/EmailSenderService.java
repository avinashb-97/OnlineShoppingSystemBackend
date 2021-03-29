package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public interface EmailSenderService {

    public void sendEmail(String toMail, String subject, String message);

    public String getOrderSuccessMailTemplate(Order order);

}
