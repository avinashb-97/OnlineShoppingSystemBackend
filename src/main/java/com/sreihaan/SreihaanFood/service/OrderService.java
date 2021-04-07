package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;

public interface OrderService {

    public Order makeOrderForCurrentUser(long addressId);

    Page<Order> getAllOrdersForAdmin(OrderPage orderPage);

    Page<Order> getAllOrdersForCurrentUser(OrderPage orderPage);

    Order updateOrderStatus(String orderId, Status status);

    Order getOrderByIdForAdmin(long id);

    Order getOrderByOrderIdForAdmin(String orderId);

    Order getOrderById(long id);

    public Order getOrderByOrderId(String orderId);

    Order makeOrderForAdmin(String email, Address address, Hashtable<Long, Long> productIdVsQuantity, Hashtable<Long, BigDecimal> productIdVsPrice);

    Page<Order> getAllOrdersUserByEmail(String email, OrderPage page);
}
