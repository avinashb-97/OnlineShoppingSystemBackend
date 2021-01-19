package com.sreihaan.SreihaanFood.model.requests;

import com.sreihaan.SreihaanFood.model.persistence.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

    private Role role;
}
