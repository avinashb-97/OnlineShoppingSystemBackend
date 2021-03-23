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
import com.sreihaan.SreihaanFood.utils.CategoryUtil;
import com.sreihaan.SreihaanFood.utils.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    public Product addProduct(Product product, Long categoryId, Long subCategoryId, MultipartFile imageFile)
    {
        product = setImageAndCategoryForProduct(product, categoryId, subCategoryId, imageFile);
        product = productRepository.save(product);
        return product;
    }

    private Product setImageAndCategoryForProduct(Product product, Long categoryId, Long subCategoryId, MultipartFile imageFile)
    {
        product.setCategory(getProductCategory(categoryId, subCategoryId));
        product = setImageForProduct(product, imageFile);
        return product;
    }

    private Category getProductCategory(Long parentCategory, Long childCategory)
    {
        Category category = categoryService.getCategoryById(parentCategory);
        Category subCategory = childCategory != null ? categoryService.getCategoryById(childCategory) : null;
        CategoryUtil.checkIsParentAndChildCategory(subCategory, category);
        return subCategory != null ? subCategory : category;
    }

    private Product setImageForProduct(Product product, MultipartFile imageFile)
    {
        if(imageFile != null)
        {
            try
            {
                Image image = imageService.saveImage(imageFile, product);
                product.setImage(image);
            }
            catch (IOException e)
            {
                logger.info("Exception while setting image for product, Id -> ", product.getId()," name -> "+product.getName()+" Exp -> "+e.getMessage());
            }
        }
        return product;
    }

    @Override
    public Product updateProduct(long productId, Product productObj, Long categoryId, Long subCategoryId, MultipartFile imageFile)
    {
        Product product = getProductById(productId);
        Category category = product.getCategory();
        product = ProductUtil.copyPropertiesForUpdate(productObj, product);
        if(category.getId() != categoryId && category.getId() != subCategoryId)
        {
            Category newCategory = categoryService.updateCategoryForProduct(getProductCategory(categoryId, subCategoryId), product);
            product.setCategory(newCategory);
        }
        product = setImageForProduct(product, imageFile);
        return productRepository.save(product);
    }

    @Override
    public List<Product> searchProducts(String name, Long categoryId, int size) {

        List<Product> products = null;
        if(categoryId != null)
        {
            Category category = categoryService.getCategoryById(categoryId);
            products = productRepository.findByNameContainingAndCategory(name, category);
        }
        else
        {
            products = productRepository.findByNameContaining(name);
        }
        size = products.size() < size ? products.size() : size;
        return products.subList(0, size);

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
