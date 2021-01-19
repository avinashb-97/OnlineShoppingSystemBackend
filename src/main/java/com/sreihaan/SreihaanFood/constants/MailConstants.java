package com.sreihaan.SreihaanFood.constants;

import javax.servlet.http.PushBuilder;

public class MailConstants {

    public static final String USERNAME = "sreihaanfoods@gmail.com";
    public static final String APP_PASSWORD = "nbqxeaqsujqzainf";
    public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    public static final String PROTOCOL = "smtp";
    public static final String HOST = "smtp.gmail.com";
    public static final String PORT = "465";
    public static final String DEBUG = "true";
    public static final String START_TLS = "true";
    public static final String AUTH = "true";
    public static final String ENABLE_SSL = "true";
    public static final String TEST_CONNECTION = "true";
    public static final String FALLBACK = "false";

    public static final String USER_CONFIRMATION_ENDPOINT = "/api/user/confirm-account";
    public static final String PASSWORD_RESET_ENDPOINT = "/api/user/reset-password";

    public static final String USER_CONFIRMATION_SUBJECT = "Complete Registration !";
    public static final String USER_CONFIRMATION_BODY = "To confirm your account, please click here : ";
    public static final String PASSWORD_RESET_SUBJECT = "Password Rest Link";
    public static final String PASSWORD_RESET_BODY = "To reset your password, please click here : ";

    public static final String EMAIL_NOREPLY_NOTE = "<br>Note: Do not reply to this email. This email is sent from an unattended mailbox. Replies will not be read.";
}
