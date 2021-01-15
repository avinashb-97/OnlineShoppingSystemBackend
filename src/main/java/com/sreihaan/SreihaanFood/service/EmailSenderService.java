package com.sreihaan.SreihaanFood.service;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public interface EmailSenderService {

    public void sendEmail(String toMail, String subject, String message);

}
