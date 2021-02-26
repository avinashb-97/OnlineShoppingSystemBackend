package com.sreihaan.SreihaanFood.service;

public interface OTPService {

    public int generateOTP(String email);

    public int getOtp(String email);

    public void clearOTP(String email);
}
