package com.sreihaan.SreihaanFood.model.persistence;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String itemName;

    private long quantity;

    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;


}
