package com.sreihaan.SreihaanFood.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    private long quantity;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;

    @Override
    public boolean equals(Object o){
        CartItem item = (CartItem) o;
        return item!= null && item.getProduct() == this.product;
    }

}
