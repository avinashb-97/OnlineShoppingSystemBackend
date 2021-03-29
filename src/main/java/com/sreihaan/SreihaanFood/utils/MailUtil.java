package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

public class MailUtil {

    public static String getUserConfirmationLink(String confirmationToken)
    {
        return AuthUtil.getBaseUrl()+ MailConstants.USER_CONFIRMATION_ENDPOINT+"?token="+confirmationToken;
     }

    private static String getUserPasswordResetLink(String passwordToken)
    {
        return AuthUtil.getBaseUrl()+ MailConstants.PASSWORD_RESET_ENDPOINT+"?token="+passwordToken;
    }

    public static String getUserPasswordResetMessage(String passwordToken)
    {
        return MailConstants.PASSWORD_RESET_BODY+ getUserPasswordResetLink(passwordToken)+ MailConstants.EMAIL_NOREPLY_NOTE;
    }

}
