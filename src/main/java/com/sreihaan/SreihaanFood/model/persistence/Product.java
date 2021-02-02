package com.sreihaan.SreihaanFood.model.persistence;

import io.github.kaiso.relmongo.annotation.ManyToOne;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "product")
public class Product {

    @Id
    private long id;

    private String name;

    private String code;

    private String productSize;

    private String cartonSize;

    private long pcsPerBag;

    private long bagsPerCtn;

    private long pcsPerCtn;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private boolean isNew = false;

    private boolean isFeatured = false;

    private boolean isBestSeller = false;

    private String subCategory;

    @OneToOne(mappedBy = "product")
    private Image image;

    @ManyToOne(mappedBy = "products")
    private Category category;
}
