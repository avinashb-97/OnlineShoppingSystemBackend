package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Order;

public interface OrderService {

    public Order makeOrderForCurrentUser(long addressId);

}
