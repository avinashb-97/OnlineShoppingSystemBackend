package com.sreihaan.SreihaanFood.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTO {

    private long productId;

    private long quantity;

    private BigDecimal unitPrice;

}
