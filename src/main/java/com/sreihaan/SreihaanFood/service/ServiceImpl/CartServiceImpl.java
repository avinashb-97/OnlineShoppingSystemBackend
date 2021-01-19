package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.Counter;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.repository.CartRepository;
import com.sreihaan.SreihaanFood.service.CartService;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.ProductService;
import com.sreihaan.SreihaanFood.service.UserService;
import com.sreihaan.SreihaanFood.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Iterator;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CounterService counterService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setCartContents(null);
        cart.setUser(user);
        cart.setId(counterService.getNextSequence("cart"));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart() {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        return cart;
    }

    @Override
    public Cart addToCart(Hashtable<Long, Long> productQuantityTable) {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        Hashtable<Long, Long> cartContent = new Hashtable<>();
        BigDecimal total = new BigDecimal(0);

        Iterator<Long> iterator = productQuantityTable.keySet().iterator();
        while (iterator.hasNext())
        {
            long productId = iterator.next();
            long quantity = productQuantityTable.get(productId);
            Product product = productService.getProductById(productId);
            BigDecimal price = product.getDiscountPrice() != null ? product.getDiscountPrice() : product.getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
            cartContent.put(product.getId(), quantity);
        };
        cart.setTotal(total);
        cart.setCartContents(cartContent);
        return cartRepository.save(cart);
    }

    @Override
    public void removeAllFromCart() {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        cart.setCartContents(null);
        cart.setTotal(new BigDecimal(0));
        cartRepository.save(cart);
    }

}
