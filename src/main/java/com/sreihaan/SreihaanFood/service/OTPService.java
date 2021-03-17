package com.sreihaan.SreihaanFood.service;

public interface OTPService {

    public Integer generateOTP(String email);

    public Integer getOtp(String email);

    public void clearOTP(String email);
}
