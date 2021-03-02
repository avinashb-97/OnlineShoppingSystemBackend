package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nationalized
    private String name;

    private String code;

    private String productSize;

    private String cartonSize;

    private long pcsPerBag;

    private long bagsPerCtn;

    private long pcsPerCtn;

    @Nationalized
    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isNew = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isFeatured = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isBestSeller = false;

    @OneToOne(mappedBy = "product",orphanRemoval = true, cascade = CascadeType.ALL)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private CartItem cartItem;

}
