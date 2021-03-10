package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CartDTO {

    private List<CartItemDTO> cartItems;

    public static CartDTO convertEntityToCartDTO(Cart cart)
    {
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem cartItem : cart.getCartItemList())
        {
            CartItemDTO cartItemDTO = new CartItemDTO();
            Product product = cartItem.getProduct();
            cartItemDTO.setProductId(product.getId());
            cartItemDTO.setUnitPrice(product.getPrice());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTOList.add(cartItemDTO);
        }
        cartDTO.setCartItems(cartItemDTOList);
        return cartDTO;
    }

}
