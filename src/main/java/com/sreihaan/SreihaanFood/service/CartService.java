package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.User;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public interface CartService {

    public Cart addToCart(Long productId, Long quantity);
    Cart addToCart(Hashtable<Long, Long> itemVsQuantity);

    public Cart removeAllFromCart();

    public Cart createCart(User user);

    public Cart getCart();

    Cart removeProductFromCart(Long productId);
}
