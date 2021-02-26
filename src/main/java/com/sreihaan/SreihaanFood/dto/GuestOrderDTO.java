package com.sreihaan.SreihaanFood.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GuestOrderDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phone;

    private String postCode;

    private String state;

    private String country;

    private Map<Long, Long> cartContents;


}
