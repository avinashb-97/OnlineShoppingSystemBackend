package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.OrderDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestBody OrderPage orderPage)
    {
        Page<Order> orderList = orderService.getAllOrdersForAdmin(orderPage);
        Page<OrderDTO> orderDTOPage = OrderDTO.convertEntityListToPage(orderList);
        return ResponseEntity.ok(orderDTOPage);
    }
}
