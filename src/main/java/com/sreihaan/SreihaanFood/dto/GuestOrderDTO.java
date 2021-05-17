package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GuestOrderDTO {

    private String email;

    private AddressDTO address;

    private List<CartItemDTO> products;

}
