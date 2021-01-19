package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.User;

import java.util.Hashtable;

public interface CartService {

    public Cart addToCart(Hashtable<Long, Long> productQuantityTable);

    public void removeAllFromCart();

    public Cart createCart(User user);

    public Cart getCart();
}
