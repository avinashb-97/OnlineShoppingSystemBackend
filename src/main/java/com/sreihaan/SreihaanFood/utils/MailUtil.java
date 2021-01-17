package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.constants.SecurityConstants;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class MailUtil {

    private static String getCurrentUrl()
    {
        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        return (currentUrl.split(SecurityConstants.SIGN_UP_URL))[0];
    }

    public static String getUserConfirmationLink(String confirmationToken)
    {
        return getCurrentUrl()+ MailConstants.USER_CONFIRMATION_ENDPOINT+"?token="+confirmationToken;
     }

    public static String getUserPasswordResetLink(String passwordToken)
    {
        return getCurrentUrl()+ MailConstants.PASSWORD_RESET_ENDPOINT+"?token="+passwordToken;
    }

}
