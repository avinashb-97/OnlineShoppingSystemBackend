package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    public Order makeOrderForCurrentUser(long addressId);

    Page<Order> getAllOrdersForAdmin(OrderPage orderPage);

    Page<Order> getAllOrdersForCurrentUser(OrderPage orderPage);

    Order updateOrderStatus(long id, Status status);

    Order getOrderByIdForAdmin(long id);

    Order getOrderById(long id);
}
