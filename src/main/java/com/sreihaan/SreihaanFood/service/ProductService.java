package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Product;

import java.util.List;

public interface ProductService {

    public Product addProduct(Product product, Long categoryId);

    public List<Product> getAllProducts();

    public Product getProductByName(String name);

    public Product getProductById(Long id);

}
