package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.constants.OrderConstants;
import com.sreihaan.SreihaanFood.exception.DataNotFoundException;
import com.sreihaan.SreihaanFood.exception.InvalidDataException;
import com.sreihaan.SreihaanFood.model.page.OrderPage;
import com.sreihaan.SreihaanFood.model.persistence.*;
import com.sreihaan.SreihaanFood.model.persistence.enums.Status;
import com.sreihaan.SreihaanFood.model.persistence.repository.OrderRepository;
import com.sreihaan.SreihaanFood.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
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

    @Autowired
    private ProductService productService;

    @Override
    public Order makeOrderForCurrentUser(long addressId) {
        User user = userService.getCurrentUser();
        Address address = addressService.getAddressById(addressId);
        return makeOrderForUser(address, user);
    }

    private Order makeOrderForUser(Address address, User user)
    {
        if( address.getUser().getId() != user.getId())
        {
            throw new InvalidDataException("Given user has and address id doesn't match, addressId -> "+address.getId());
        }
        Cart cart = user.getCart();
        Order order = new Order();
        String orderId = getOrderId();
        order.setOrderId(orderId);
        order.setAddress(address);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getCartItemList())
        {
            Product product = cartItem.getProduct();
            long quantity = cartItem.getQuantity();
            BigDecimal price = product.getProductPrice();
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));
            OrderItem orderItem = setDataAndGetOrderItem(product, price, quantity, totalPrice);
            orderItems.add(orderItem);
            orderItem.setOrder(order);
            totalAmount = totalAmount.add(totalPrice);
        }
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotal(totalAmount);
        order.setStatus(Status.ORDERED);
        cartService.removeAllFromCart();
        return orderRepository.save(order);
    }

    private OrderItem setDataAndGetOrderItem(Product product, BigDecimal price, long quantity, BigDecimal totalPrice)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(product.getName());
        orderItem.setItemCode(product.getCode());
        orderItem.setCategory(product.getCategory().getName());
        orderItem.setQuantity(quantity);
        orderItem.setUnitPrice(price);
        orderItem.setTotalPrice(totalPrice);
        return orderItem;
    }

    private String getOrderId()
    {
        long timeStamp = System.currentTimeMillis()/1000;
        String orderId = String.valueOf(timeStamp) + (long)(Math.random()*100);
        if(orderRepository.existsOrderByOrderId(orderId))
        {
            timeStamp = System.currentTimeMillis()/1000;
            orderId = (char)(Math.random()*26 + 'A') + String.valueOf(timeStamp) + (long)(Math.random()*10);
        }
        return orderId;
    }

    private Pageable getOrderPage(OrderPage orderPage)
    {
        Sort sort = Sort.by(orderPage.getSortDirection(), orderPage.getSortBy());
        return PageRequest.of(orderPage.getPageNumber(), orderPage.getPageSize(), sort);
    }

    @Override
    public Page<Order> getAllOrdersForAdmin(OrderPage page) {
        Pageable orderPagable = getOrderPage(page);
        Date startDate = null;
        Date endDate = null;
        if(page.getStartDate() == null && page.getEndDate() == null)
        {

            Instant now = Instant.now();
            Instant start = now.minus(Duration.ofDays(OrderConstants.MAX_ORDER_FETCH_DAYS));
            startDate = Date.from(start);
            endDate = new Date();
        }
        else
        {
            if(page.getStartDate() == null || page.getEndDate() == null)
            {
                throw new InvalidDataException("Both start date and end date should have data");
            }
            startDate = page.getStartDate();
            endDate = page.getEndDate();
            if((endDate.getTime()-startDate.getTime())/ (1000 * 60 * 60 * 24) > OrderConstants.MAX_ORDER_FETCH_DAYS)
            {
                throw new InvalidDataException("Difference between start and end date is greater than "+OrderConstants.MAX_ORDER_FETCH_DAYS);
            }
        }
        if(page.getStatus() == null)
        {
            return orderRepository.findAllByCreatedTimeLessThanEqualAndCreatedTimeGreaterThanEqual(orderPagable, endDate, startDate);
        }
        else
        {
            return orderRepository.findAllByStatusAndCreatedTimeLessThanEqualAndCreatedTimeGreaterThanEqual(page.getStatus(), orderPagable, endDate, startDate);
        }
    }

    private Order getOrder(long id)
    {
        return orderRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Order not found, id -> "+id));
    }

    private Order getOrder(String orderId)
    {
        return orderRepository.findOrderByOrderId(orderId)
                .orElseThrow(()-> new DataNotFoundException("Order not found, orderId -> "+orderId));
    }

    @Override
    public Order updateOrderStatus(String orderId, Status status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        if(status.equals(Status.DELIVERED))
        {
            order.setDeliveryTime(new Date());
        }
        return order;
    }

    @Override
    public Order getOrderByIdForAdmin(long id) {
        return getOrder(id);
    }

    @Override
    public Order getOrderByOrderIdForAdmin(String orderId) {
        return getOrder(orderId);
    }

    @Override
    public Order getOrderById(long id) {
        Order order = getOrder(id);
        User currentUser = userService.getCurrentUser();
        if(order.getUser().getId() != currentUser.getId())
        {
            throw new DataNotFoundException("No order found for the user with given id. id -> "+id);
        }
        return order;
    }

    @Override
    public Order getOrderByOrderId(String orderId) {
        Order order = getOrder(orderId);
        User currentUser = userService.getCurrentUser();
        if(order.getUser().getId() != currentUser.getId())
        {
            throw new DataNotFoundException("No order found for the user with given id. orderId -> "+orderId);
        }
        return order;
    }

    @Override
    public Order makeOrderForAdmin(String email, Address address, Hashtable<Long, Long> productIdVsQuantity, Hashtable<Long, BigDecimal> productIdVsPrice)
    {
        User user = null;
        try {
            user = userService.getUserByEmail(email);
        }
        catch (Exception ex)
        {
            user = userService.createDummyUser(email);
        }
        User adminUser = userService.getCurrentUser();
        Address savedAddress = addressService.addAddress(address);
        Order order = makeOrder(address, user, productIdVsQuantity, productIdVsPrice, adminUser.getEmail());
        return order;
    }

    @Override
    public Page<Order> getAllOrdersForCurrentUser(OrderPage page) {
        User user = userService.getCurrentUser();
        return getAllOrdersForUser(user, page);
    }

    @Override
    public Page<Order> getAllOrdersUserByEmail(String email, OrderPage page) {
        User user = userService.getUserByEmail(email);
        return  getAllOrdersForUser(user, page);
    }

    public Page<Order> getAllOrdersForUser(User user, OrderPage page)
    {
        Pageable orderPagable = getOrderPage(page);
        if(page.getStatus() == null)
        {
            return orderRepository.findAllByUser(user, orderPagable);
        }
        else
        {
            return orderRepository.findAllByUserAndStatus(user, page.getStatus(), orderPagable);
        }
    }


    private Order makeOrder(Address address, User user, Hashtable<Long,Long> productIdVsQuantity, Hashtable<Long, BigDecimal> productIdVsPrice, String orderedBy)
    {
        Order order = new Order();
        order.setUser(user);
        String orderId = getOrderId();
        order.setOrderId(orderId);
        order.setAddress(address);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(long productId : productIdVsQuantity.keySet())
        {
            long quantity = productIdVsQuantity.get(productId);
            Product product = productService.getProductById(productId);
            BigDecimal price = productIdVsPrice.get(productId);
            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));
            OrderItem orderItem = setDataAndGetOrderItem(product, price, quantity, totalPrice);
            orderItems.add(orderItem);
            orderItem.setOrder(order);
            totalAmount = totalAmount.add(totalPrice);
        }
        order.setOrderItems(orderItems);
        order.setTotal(totalAmount);
        order.setStatus(Status.ORDERED);
        order.setOrderedByAdmin(true);
        order.setOrderedBy(orderedBy);
        return orderRepository.save(order);
    }

}
