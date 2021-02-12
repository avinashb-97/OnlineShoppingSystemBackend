package com.sreihaan.SreihaanFood.model.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nationalized
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parentid")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> childCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Product> products;

}
