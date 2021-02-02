package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Product addProduct(Product product, Long categoryId, MultipartFile imageFile);

    public List<Product> getAllProducts();

    public Product getProductByName(String name);

    public Product getProductById(Long id);

    Image getImage(long productId);

    List<Product> getBestSellers();

    List<Product> getFeaturedProducts();
}
