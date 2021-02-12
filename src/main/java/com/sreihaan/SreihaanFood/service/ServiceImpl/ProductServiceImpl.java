package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.controller.ProductController;
import com.sreihaan.SreihaanFood.exception.ProductNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.repository.ProductRepository;
import com.sreihaan.SreihaanFood.service.CategoryService;
import com.sreihaan.SreihaanFood.service.ImageService;
import com.sreihaan.SreihaanFood.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public Product addProduct(Product product, Long categoryId, Long subCategroyId, MultipartFile imageFile)
    {
        Category category = categoryService.getCategoryById(categoryId);
        Category childCategory = subCategroyId != null ? categoryService.getCategoryById(subCategroyId) : null;
        categoryService.checkIsParentAndChildCategory(childCategory, category);
        product.setCategory(childCategory != null ? childCategory : category);
        Image image = null;
        try
        {
            if(imageFile != null)
            {
                image = imageService.saveImage(imageFile, product);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        product.setImage(image);
        product = productRepository.save(product);
        return product;

    }

    @Override
    public List<Product> getAllProducts() {
        long start = System.currentTimeMillis();
        List<Product> products = productRepository.findAll();
        long timeTaken = System.currentTimeMillis() - start;
        logger.info("Time taken to get all products from DB : "+timeTaken);
        return products;
    }

    @Override
    public Product getProductByName(String name) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found -> Id : "+id));
    }

    @Override
    public Image getImage(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("Product not found, Id -> "+productId));
        return imageService.getImage(product);
    }

    @Override
    public List<Product> getBestSellers() {
        return productRepository.findByIsBestSeller(true);
    }

    @Override
    public List<Product> getFeaturedProducts() {
        return productRepository.findByIsFeatured(true);
    }

    @Override
    public void removeProduct(long productId)
    {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public Set<Product> getProductsForCategory(long categoryId) {
        return productRepository.findByAssociatedWithCategory(categoryId);
    }


}
