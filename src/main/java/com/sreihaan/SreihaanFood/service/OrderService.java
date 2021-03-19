package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    public Order makeOrderForCurrentUser(long addressId);

    Page<Order> getAllOrdersForAdmin(OrderPage orderPage);

    Page<Order> getAllOrdersForCurrentUser(OrderPage orderPage);
}
