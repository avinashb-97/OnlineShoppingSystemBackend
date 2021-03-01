package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;


@Getter
@Setter
public class Address {

    @Id
    private long id;

    private String customerName;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String phone;

}
