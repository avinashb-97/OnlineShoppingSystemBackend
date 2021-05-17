package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Order;

public interface EmailSenderService {

    public void sendEmail(String toMail, String subject, String message);

    public void sendOrderConfirmationMail(Order order);

    public void sendOTPForUser(String email, int otp, String subject);

    void sendErrorMessageForUserCreation(String email);

    public void sendQueryMail(String name, String email, String query);

    void sendOrderConfirmationMailForGuest(String customerName, Order order);

    void sendGuestOrderConfirmationMailForAdmin(String customerName, Order order);
}
