package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.*;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import com.sreihaan.SreihaanFood.model.persistence.repository.OrderRepository;
import com.sreihaan.SreihaanFood.service.AddressService;
import com.sreihaan.SreihaanFood.service.CartService;
import com.sreihaan.SreihaanFood.service.OrderService;
import com.sreihaan.SreihaanFood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Order makeOrderForCurrentUser(long addressId) {
        Address address = addressService.getAddressById(addressId);
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        Order order = new Order();
        order.setAddress(address);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getCartItemList())
        {
            Product product = cartItem.getProduct();
            long quantity = cartItem.getQuantity();
            BigDecimal price = product.getProductPrice();
            OrderItem orderItem = new OrderItem();
            orderItem.setItemName(product.getName());
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(price);
            orderItem.setTotalPrice(product.getProductPrice().multiply(new BigDecimal(quantity)));
            orderItems.add(orderItem);
            orderItem.setOrder(order);
            totalAmount = totalAmount.add(price);
        }
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotal(totalAmount);
        order.setStatus(Status.ORDERED);
        cartService.removeAllFromCart();
        return orderRepository.save(order);
    }

    private Pageable getOrderPage(OrderPage orderPage)
    {
        Sort sort = Sort.by(orderPage.getSortDirection(), orderPage.getSortBy());
        return PageRequest.of(orderPage.getPageNumber(), orderPage.getPageSize(), sort);
    }

    @Override
    public Page<Order> getAllOrdersForAdmin(OrderPage page) {
        Pageable orderPagable = getOrderPage(page);
        return orderRepository.findAll(orderPagable);
    }

    @Override
    public Page<Order> getAllOrdersForCurrentUser(OrderPage page) {
        User user = userService.getCurrentUser();
        Pageable orderPagable = getOrderPage(page);
        return orderRepository.findAllByUser(user, orderPagable);
    }
}
