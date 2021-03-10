package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CartDTO;
import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import com.sreihaan.SreihaanFood.exception.InvalidDataException;
import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.persistence.CartItem;
import com.sreihaan.SreihaanFood.model.requests.ModifyCartRequest;
import com.sreihaan.SreihaanFood.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping()
    public ResponseEntity<CartDTO> getCart()
    {
        Cart cart = cartService.getCart();
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

    @PostMapping("/{id}")
    public ResponseEntity<CartDTO> addToCart(@PathVariable Integer id,@RequestBody CartItemDTO cartItem)
    {
        if(id != cartItem.getProductId())
        {
            throw new InvalidDataException("product id mismatch");
        }
        Cart cart = cartService.addToCart(cartItem.getProductId(), cartItem.getQuantity());
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

    @PostMapping()
    public ResponseEntity<CartDTO> addToCart(@RequestBody List<CartItemDTO> cartItemDTOS)
    {
        Hashtable<Long, Long> itemVsQuantity = new Hashtable();
        for(CartItemDTO cartItemDTO : cartItemDTOS)
        {
            itemVsQuantity.put(cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        }
        Cart cart = cartService.addToCart(itemVsQuantity);
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long productId)
    {
        Cart cart = cartService.removeProductFromCart(productId);
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

    @DeleteMapping()
    public ResponseEntity<CartDTO> emptyCart()
    {
        Cart cart = cartService.removeAllFromCart();
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

}
