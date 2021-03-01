package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/mail/{mailId}")
    public void sendMail(@PathVariable String mailId) {
        emailSenderService.sendEmail(mailId, "Test", "Hello Test 123 !");
    }

    @PostMapping("/mail2/{mailId}")
    public void sendMail2(@PathVariable String mailId) {
        String to = "avinashbb695@gmail.com";

        String from = "sreihaanfoods@gmail.com";
        final String username = "sreihaanfoods@gmail.com";//your Gmail username
        final String password = "nbqxeaqsujqzainf";//your Gmail password

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);

        // Get the Session object
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject
            message.setSubject("Hi JAXenter");

            // Put the content of your message
            message.setText("Hi there,we are just experimenting with JavaMail here");

// Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }


    @PostMapping("mail3")
    public void prepareAndSendEmail()
    {

        String toMailId = "avinashbb695@gmail.com";
        String htmlMessage = "Hello world!";
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);

        mailSender.setUsername(String.valueOf("sreihaanfoods@gmail.com"));
        mailSender.setPassword(String.valueOf("rforwkqoekaghjeg"));

//        mailSender.setPassword(String.valueOf("!Droid@1A"));


        Properties mailProp = mailSender.getJavaMailProperties();
        mailProp.put("mail.transport.protocol", "smtp");
        mailProp.put("mail.smtp.auth", "true");
        mailProp.put("mail.smtp.starttls.enable", "true");
        mailProp.put("mail.smtp.starttls.required", "true");
        mailProp.put("mail.debug", "true");
        mailProp.put("mail.smtp.ssl.enable", "true");

        mailProp.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toMailId);
            helper.setSubject("Welcome to Subject Part");
            helper.setText(htmlMessage, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
