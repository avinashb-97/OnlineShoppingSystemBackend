package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    private Set<CartItem> cartItemList;

    private BigDecimal total;

    public Cart(Cart cart)
    {
        this.id = cart.getId();
        this.user = cart.getUser();
        this.cartItemList = cart.getCartItemList();
        this.total = cart.getTotal();
    }
}
