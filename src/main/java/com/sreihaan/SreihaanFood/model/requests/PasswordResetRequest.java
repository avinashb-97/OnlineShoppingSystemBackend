package com.sreihaan.SreihaanFood.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {

    private String email;

    private int otp;

    private String password;

    private String confirmPassword;

}
