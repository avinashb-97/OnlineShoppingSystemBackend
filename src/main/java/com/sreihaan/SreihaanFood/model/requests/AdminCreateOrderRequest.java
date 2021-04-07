package com.sreihaan.SreihaanFood.model.requests;

import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateOrderRequest {

    private AddressDTO address;

    private List<CartItemDTO> cartItems;

    private String email;

}
