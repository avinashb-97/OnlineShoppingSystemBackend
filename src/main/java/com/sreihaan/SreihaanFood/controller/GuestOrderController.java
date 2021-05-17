package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.AddressDTO;
import com.sreihaan.SreihaanFood.dto.CartItemDTO;
import com.sreihaan.SreihaanFood.dto.GuestOrderDTO;
import com.sreihaan.SreihaanFood.model.persistence.Address;
import com.sreihaan.SreihaanFood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/api/guest/order")
public class GuestOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void orderGuestProducts(@RequestBody GuestOrderDTO guestOrderDTO)
    {
        List<CartItemDTO> cartItemDTOS = guestOrderDTO.getProducts();
        Hashtable<Long, Long> itemVsQuantity = new Hashtable();
        for(CartItemDTO cartItemDTO : cartItemDTOS)
        {
            itemVsQuantity.put(cartItemDTO.getProductId(), cartItemDTO.getQuantity());
        }
        Address address = AddressDTO.convertAddressDTOToEntity(guestOrderDTO.getAddress());
        orderService.makeOrderForGuestUser(guestOrderDTO.getEmail(), address, itemVsQuantity);
    }

}
