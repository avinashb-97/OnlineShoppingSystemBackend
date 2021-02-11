package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name="category_entity_seq_gen", sequenceName="CATEGORY_ENTITY_SEQ")
    private long id;

    @Nationalized
    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubCategory> subCategories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    public void setProducts(Product product) {
        if(products == null)
        {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    public void removeSubCategory(SubCategory subCategory)
    {
        this.subCategories.remove(subCategory);
    }

    public void addSubCategory(SubCategory subCategory)
    {
        if(this.subCategories == null)
        {
            this.subCategories = new ArrayList<>();
        }
        this.subCategories.add(subCategory);
    }
}
