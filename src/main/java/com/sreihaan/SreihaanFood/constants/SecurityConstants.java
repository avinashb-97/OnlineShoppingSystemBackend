package com.sreihaan.SreihaanFood.constants;

public class SecurityConstants {

    public static final String SECRET = "oursecretkey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
    public static final String MAIN_ADMIN_EMAIL = "sreihaanfoods@gmail.com";
    public static final int PASSWORD_TOKEN_EXPIRATION = 1; // 1hour
}