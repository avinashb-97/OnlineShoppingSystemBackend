package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;

import java.util.List;

public interface CategoryService {
    
    public Category addCategory(Category category);
    
    public List<Category> getAllCategories();

    public Category getCategoryByName(String category);

    public Category getCategoryById(Long id);

    public Category updateCategory(long id, Category category);

    public List<Product> getProductsForCategory(long id);

    void deleteCategory(long categoryId);

    SubCategory getSubCategoryById(Long subCategroyId);

    SubCategory addSubCategory(SubCategory subCategory, long categoryId);

    SubCategory updateSubCategory(long id, SubCategory subCategory);

    void deleteSubCategory(long subCategoryId);
}
