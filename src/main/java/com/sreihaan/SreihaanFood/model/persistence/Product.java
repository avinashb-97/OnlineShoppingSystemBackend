package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="product_entity_seq_gen", sequenceName="PRODUCT_ENTITY_SEQ")
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
    private boolean isNew = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isFeatured = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isBestSeller = false;

    @OneToOne(mappedBy = "product")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subCategoryId", nullable = true)
    private SubCategory subCategory;
}
