package com.sreihaan.SreihaanFood.model.persistence.repository;

import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Page<Order> findAllByUser(User user, Pageable pageable);

    Page<Order> findAllByStatus(Status status, Pageable pageable);

    Page<Order> findAllByUserAndStatus(User user, Status status, Pageable orderPagable);

    boolean existsOrderByOrderId(String orderId);

    Optional<Order> findOrderByOrderId(String orderId);
}
