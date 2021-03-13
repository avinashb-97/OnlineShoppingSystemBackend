package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
public class CartDTO {

    private List<CartItemDTO> cartItems;

    public static CartDTO convertEntityToCartDTO(Cart cart)
    {
        CartDTO cartDTO = new CartDTO();
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for(CartItem cartItem : sortAndGetCartItemsList(cart))
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


    private static List<CartItem> sortAndGetCartItemsList(Cart cart)
    {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.addAll(cart.getCartItemList());
        Comparator<CartItem> cartItemComparator = new Comparator<CartItem>() {
            @Override
            public int compare(CartItem c1, CartItem c2) {
                return c1.getAddedTime().compareTo(c2.getAddedTime());
            }
        };
        Collections.sort(cartItems, cartItemComparator);
        return cartItems;
    }

}
