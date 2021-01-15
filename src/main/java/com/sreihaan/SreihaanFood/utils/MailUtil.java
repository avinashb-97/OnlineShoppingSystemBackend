package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.constants.MailConstants;
import com.sreihaan.SreihaanFood.constants.SecurityConstants;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class MailUtil {

    public static String getConfirmationLink(String confirmationToken)
    {
        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        String url = (currentUrl.split(SecurityConstants.SIGN_UP_URL))[0]+ MailConstants.USER_CONFIRMATION_ENDPOINT+"?token="+confirmationToken;
        return url;
    }

}
