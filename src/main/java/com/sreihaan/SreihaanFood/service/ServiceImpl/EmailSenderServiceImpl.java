package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.model.persistence.Address;
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

    public String getOrderSuccessMailTemplate(Order order)
    {
        Context context = new Context();
        context.setVariable("userName", order.getUser().getFirstName());
        context.setVariable("address", order.getAddress());
        context.setVariable("items", order.getOrderItems());
        context.setVariable("orderId", order.getOrderId());
        context.setVariable("orderDate", order.getCreatedTime());
        String process = templateEngine.process("emails/OrderSuccess", context);
        return process;
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
