package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="cart_entity_seq_gen", sequenceName="CART_ENTITY_SEQ")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

//    private Map<Long, Long> cartContents;

    private BigDecimal total;

    public Cart(Cart cart)
    {
        this.id = cart.getId();
        this.user = cart.getUser();
//        this.cartContents = cart.getCartContents();
        this.total = cart.getTotal();
    }
}
