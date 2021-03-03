package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.User;

import java.util.Hashtable;

public interface CartService {

    public Cart addToCart(Long productId, Long quantity);

    public Cart removeAllFromCart();

    public Cart createCart(User user);

    public Cart getCart();

    Cart removeProductFromCart(Long productId);
}
