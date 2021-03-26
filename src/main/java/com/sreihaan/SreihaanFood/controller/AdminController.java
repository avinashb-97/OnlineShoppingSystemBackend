package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.OrderDTO;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.Order;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(OrderPage orderPage)
    {
        Page<Order> orderList = orderService.getAllOrdersForAdmin(orderPage);
        Page<OrderDTO> orderDTOPage = OrderDTO.convertEntityListToPage(orderList);
        return ResponseEntity.ok(orderDTOPage);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId)
    {
        Order order = orderService.getOrderByOrderIdForAdmin(orderId);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/order/{orderId}/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable String orderId, @RequestBody Status status)
    {
        Order order = orderService.updateOrderStatus(orderId, status);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}
