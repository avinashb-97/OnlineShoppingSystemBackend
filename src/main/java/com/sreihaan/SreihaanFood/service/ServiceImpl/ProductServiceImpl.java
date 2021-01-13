package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.ImageNotFoundException;
import com.sreihaan.SreihaanFood.exception.ProductNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.repository.ProductRepository;
import com.sreihaan.SreihaanFood.service.CategoryService;
import com.sreihaan.SreihaanFood.service.CounterService;
import com.sreihaan.SreihaanFood.service.ImageService;
import com.sreihaan.SreihaanFood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CounterService counterService;

    @Autowired
    private ImageService imageService;

    @Override
    public Product addProduct(Product product, Long categoryId, MultipartFile imageFile)
    {
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        product.setId(counterService.getNextSequence("product"));
        Image image = null;
        try
        {
            image = imageService.saveImage(imageFile, product);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        product.setImage(image);
        product = productRepository.save(product);
        category.setProducts(product);
        categoryService.updateCategory(category);
        return product;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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


}
