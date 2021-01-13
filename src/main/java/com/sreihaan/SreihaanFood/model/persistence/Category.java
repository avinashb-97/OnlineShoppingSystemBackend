package com.sreihaan.SreihaanFood.model.persistence;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "category")
public class Category {

    @Id
    private long id;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinProperty(name = "products")
    private List<Product> products;

    public void setProducts(Product product) {
        if(products == null)
        {
            products = new ArrayList<>();
        }
        products.add(product);
    }
}
