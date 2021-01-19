package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.User;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
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

    private Map<Long, Long> cartContents;

    private BigDecimal total;

    public static CartDTO convertEntityToCartDTO(Cart cart)
    {
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(cart, cartDTO);
        cartDTO.setUserId(Long.valueOf(cart.getUser().getId()));
        return cartDTO;
    }

}
