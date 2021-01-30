package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.CategoryNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.repository.CategoryRepository;
import com.sreihaan.SreihaanFood.service.CategoryService;
import com.sreihaan.SreihaanFood.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CounterService counterService;

    @Override
    public Category addCategory(Category category) {
        category.setId(counterService.getNextSequence("category"));
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found -> Name : "+name));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found -> Id : "+id));
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> getProductsForCategory(long id) {
        Category category = getCategoryById(id);
        return category.getProducts();
    }
}
