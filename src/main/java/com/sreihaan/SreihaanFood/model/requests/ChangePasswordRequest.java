package com.sreihaan.SreihaanFood.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    String oldPassword;

    String password;

    String confirmPassword;

}
