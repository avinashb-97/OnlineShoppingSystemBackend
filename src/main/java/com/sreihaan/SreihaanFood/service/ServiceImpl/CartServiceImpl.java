package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.User;
import com.sreihaan.SreihaanFood.model.persistence.repository.CartItemRepository;
import com.sreihaan.SreihaanFood.model.persistence.repository.CartRepository;
import com.sreihaan.SreihaanFood.service.CartService;
import com.sreihaan.SreihaanFood.service.ProductService;
import com.sreihaan.SreihaanFood.service.UserService;
import com.sreihaan.SreihaanFood.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart() {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        return cart;
    }

    @Override
    public Cart removeProductFromCart(Long productId) {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        Product product = productService.getProductById(productId);

        Set<CartItem> cartItemSet = cart.getCartItemList();
        boolean found = false;
        for(CartItem item : cartItemSet)
        {
            Product cartProduct = item.getProduct();
            if(cartProduct.equals(product))
            {
                cartItemSet.remove(item);
                cartItemRepository.deleteCartItem(item.getId());
                found = true;
                break;
            }
        }
        if(!found)
        {
            return cart;
        }
        cart.setCartItemList(cartItemSet);
        cart = cartRepository.save(cart);
        return cart;
    }

    private void deleteCartItem(CartItem item)
    {
        cartItemRepository.deleteById(item.getId());
    }

    @Override
    public Cart addToCart(Long productId, Long quantity) {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        return addToCart(user, productId, quantity);
    }

    private Cart addToCart(User user, Long productId, Long quantity)
    {
        Cart cart = new Cart(user.getCart());
        Product product = productService.getProductById(productId);

        Set<CartItem> cartItemSet = cart.getCartItemList();
        boolean found = false;
        for(CartItem item : cartItemSet)
        {
            Product cartProduct = item.getProduct();
            if(cartProduct.equals(product))
            {
                item.setQuantity(quantity);
                found = true;
            }
        }
        if(!found)
        {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem = cartItemRepository.save(cartItem);
            cart.getCartItemList().add(cartItem);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart addToCart(Hashtable<Long, Long> itemVsQuantity) {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        for(Long productId : itemVsQuantity.keySet())
        {
            addToCart(user, productId, itemVsQuantity.get(productId));
        }
        return getCart();
    }


    private BigDecimal getProductPrice(Product product, Long quantity)
    {
        BigDecimal price = product.getDiscountPrice() != null && product.getDiscountPrice().compareTo(BigDecimal.ZERO) != 0 ? product.getDiscountPrice() : product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public Cart removeAllFromCart() {
        User user = userService.getUserByEmail(AuthUtil.getLoggedInUserName());
        Cart cart = new Cart(user.getCart());
        Set<CartItem> cartItemSet = cart.getCartItemList();
        for(CartItem item : cartItemSet)
        {
            Product cartProduct = item.getProduct();
            cartItemRepository.deleteCartItem(item.getId());
        }
        cart.setCartItemList(new HashSet<>());
        return cartRepository.save(cart);
    }

}
