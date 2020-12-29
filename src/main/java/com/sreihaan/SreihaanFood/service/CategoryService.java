package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Category;

import java.util.List;

public interface CategoryService {
    
    public Category addCategory(Category category);
    
    public List<Category> getAllCategories();

    public Category getCategoryByName(String category);

    public Category getCategoryById(Long id);
    
}
