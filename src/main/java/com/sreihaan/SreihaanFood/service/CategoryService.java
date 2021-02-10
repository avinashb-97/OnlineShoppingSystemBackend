package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;

import java.util.List;

public interface CategoryService {
    
    public Category addCategory(Category category, List<SubCategory> subCategories);
    
    public List<Category> getAllCategories();

    public Category getCategoryByName(String category);

    public Category getCategoryById(Long id);

    public Category updateCategory(Category category);

    public List<Product> getProductsForCategory(long id);

    void deleteCategory(long categoryId);

    SubCategory getSubCategoryById(Long subCategroyId);
}
