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
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestBody OrderPage orderPage)
    {
        Page<Order> orderList = orderService.getAllOrdersForAdmin(orderPage);
        Page<OrderDTO> orderDTOPage = OrderDTO.convertEntityListToPage(orderList);
        return ResponseEntity.ok(orderDTOPage);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long id)
    {
        Order order = orderService.getOrderByIdForAdmin(id);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/order/{id}/status")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable long id, @RequestBody Status status)
    {
        Order order = orderService.updateOrderStatus(id, status);
        OrderDTO orderDTO = OrderDTO.convertEntityToOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}
