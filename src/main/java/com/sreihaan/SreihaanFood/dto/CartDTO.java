package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class CartDTO {

    private long id;

    private long userId;

    //Map of productId vs numberOfProducts
    private Map<Long, Long> cartContents;

    public static CartDTO convertEntityToCartDTO(Cart cart)
    {
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(cart, cartDTO);
        cartDTO.setUserId(Long.valueOf(cart.getUser().getId()));
        return cartDTO;
    }

}
