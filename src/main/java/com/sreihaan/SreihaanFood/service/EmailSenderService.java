package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Order;

public interface EmailSenderService {

    public void sendEmail(String toMail, String subject, String message);

    public void sendOrderConfirmationMail(Order order);

    public void sendOTPForUser(String email, int otp, String subject);

    void sendErrorMessageForUserCreation(String email);
    
}
