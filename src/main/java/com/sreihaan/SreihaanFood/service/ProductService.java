package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {

    public Product addProduct(Product product, Long categoryId, Long subCategroyId, MultipartFile imageFile);

    public List<Product> getAllProducts();

    public Product getProductByName(String name);

    public Product getProductById(Long id);

    Image getImage(long productId);

    List<Product> getBestSellers();

    List<Product> getFeaturedProducts();

    public void removeProduct(long productId);

    Set<Product> getProductsForCategory(long categoryId);

    Product updateProduct(long productId, Product product, Long categoryId, Long subCategoryId, MultipartFile image);

    List<Product> searchProducts(String key, Long categoryId, int size);
}
