package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendEmail(String toMail, String subject, String message)  {
        try
        {
            Session session = Session.getDefaultInstance(getMailProperties());
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(MailConstants.USERNAME));
            InternetAddress[] toAddresses = { new InternetAddress(toMail) };
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent(message, "text/html");

            Transport t = session.getTransport(MailConstants.PROTOCOL);
            t.connect(MailConstants.USERNAME, MailConstants.APP_PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getOrderSuccessMailTemplate(Order order, String userName)
    {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("address", order.getAddress());
        context.setVariable("items", order.getOrderItems());
        context.setVariable("orderId", order.getOrderId());
        context.setVariable("orderDate", order.getCreatedTime());
        context.setVariable("totalAmount", order.getTotal());
        String process = templateEngine.process("emails/OrderSuccess", context);
        return process;
    }

    private String getOrderSuccessMailTemplateForAdmin(Order order, String userName)
    {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("email", order.getOrderedBy());
        context.setVariable("address", order.getAddress());
        context.setVariable("items", order.getOrderItems());
        context.setVariable("orderId", order.getOrderId());
        context.setVariable("orderDate", order.getCreatedTime());
        context.setVariable("totalAmount", order.getTotal());
        context.setVariable("phone", order.getAddress().getPhone());
        String process = templateEngine.process("emails/OrderSuccessAdmin", context);
        return process;
    }

    @Async
    public void sendQueryMail(String name, String email, String query)
    {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("query", query);
        String message = templateEngine.process("emails/QueryMail", context);
        sendEmail(MailConstants.ADMIN_MAIL, "Query - "+email, message);
    }

    @Override
    @Async
    public void sendOrderConfirmationMailForGuest(String customerName, Order order) {
        String message = getOrderSuccessMailTemplate(order, customerName);
        sendEmail(order.getOrderedBy(), "Order Confirmation - Order Id: "+order.getOrderId(), message);
    }

    @Override
    @Async
    public void sendGuestOrderConfirmationMailForAdmin(String customerName, Order order) {
        String message = getOrderSuccessMailTemplateForAdmin(order, customerName);
        sendEmail(MailConstants.ADMIN_MAIL, "Guest Order Confirmation - Order Id: "+order.getOrderId(), message);
    }

    @Async
    public void sendOrderConfirmationMail(Order order)
    {
        String message = getOrderSuccessMailTemplate(order, order.getUser().getFirstName());
        sendEmail(order.getUser().getEmail(), "Order Confirmation - Order Id: "+order.getOrderId(), message);
    }

    @Async
    public void sendOTPForUser(String email, int otp, String subject)
    {
        Context context = new Context();
        context.setVariable("otp", otp);
        String message = templateEngine.process("emails/UserOTP", context);
        sendEmail(email, subject, message);
    }

    @Async
    public void sendErrorMessageForUserCreation(String email) {
        Context context = new Context();
        String message = templateEngine.process("emails/UserAlreadyExistsError", context);
        sendEmail(email, "OTP - User Registration", message);
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", MailConstants.HOST);
        properties.setProperty("mail.smtp.socketFactory.class", MailConstants.SSL_FACTORY);
        properties.setProperty("mail.transport.protocol", MailConstants.PROTOCOL);
        properties.setProperty("mail.smtp.auth", MailConstants.AUTH);
        properties.setProperty("mail.smtp.starttls.enable", MailConstants.START_TLS);
        properties.setProperty("mail.smtp.starttls.required", "false");
        properties.setProperty("mail.debug", MailConstants.DEBUG);
        properties.setProperty("mail.smtp.ssl.enable", MailConstants.ENABLE_SSL);
        properties.setProperty("mail.test-connection", MailConstants.TEST_CONNECTION);
        properties.setProperty("mail.smtp.socketFactory.fallback", MailConstants.FALLBACK);
        properties.setProperty("mail.smtp.port", MailConstants.PORT);
        properties.setProperty("mail.smtp.socketFactory.port", MailConstants.PORT);
        properties.put("mail.smtp.timeout", MailConstants.TIME_OUT);
        properties.put("mail.smtp.connectiontimeout", MailConstants.TIME_OUT);
        properties.put("mail.smtp.writetimeout", MailConstants.TIME_OUT);
        properties.put("mail.smtp.ssl.trust", "*");
        return properties;
    }
}
