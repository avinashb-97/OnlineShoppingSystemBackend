package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CartDTO;
import com.sreihaan.SreihaanFood.model.persistence.Cart;
import com.sreihaan.SreihaanFood.model.requests.ModifyCartRequest;
import com.sreihaan.SreihaanFood.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<CartDTO> addToCart(@RequestBody ModifyCartRequest modifyCartRequest)
    {
        Cart cart = cartService.addToCart(modifyCartRequest.getProducts());
        return ResponseEntity.ok(CartDTO.convertEntityToCartDTO(cart));
    }

    @DeleteMapping()
    public void emptyCart()
    {
        cartService.removeAllFromCart();
    }

}
