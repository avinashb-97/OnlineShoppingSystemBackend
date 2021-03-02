package com.sreihaan.SreihaanFood.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

    @Id
    private long id;

    @OneToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private long quantity;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @Override
    public boolean equals(Object o){
        CartItem item = (CartItem) o;
        return item!= null && item.getProduct().getId() == this.product.getId();
    }

}
