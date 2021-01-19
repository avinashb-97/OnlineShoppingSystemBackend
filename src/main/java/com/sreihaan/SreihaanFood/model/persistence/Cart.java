package com.sreihaan.SreihaanFood.model.persistence;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "cart")
public class Cart {

    @Id
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinProperty(name = "user")
    private User user;

    private Map<Long, Long> cartContents;

    private BigDecimal total;

    public Cart(Cart cart)
    {
        this.id = cart.getId();
        this.user = cart.getUser();
        this.cartContents = cart.getCartContents();
        this.total = cart.getTotal();
    }
}
