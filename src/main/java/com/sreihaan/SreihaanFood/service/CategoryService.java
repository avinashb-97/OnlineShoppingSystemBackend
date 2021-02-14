package com.sreihaan.SreihaanFood.service;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    
    public Category createCategory(Category category);
    
    public List<Category> getAllCategories();

    public Category getCategoryByName(String category);

    public Category getCategoryById(Long id);

    public Category updateCategoryDetails(long id, Category category);

    public Category updateCategoryDetails(Category category);

    void deleteCategory(long categoryId);

    Category createAndAddSubCategory(Long parentid, Category subCategory);

    Category updateSubCategoryDetails(Long parentId, Long childId, Category subCategory);

    void deleteSubCategory(long parentId, long subCategoryId);

    Set<Category> getSubCategories(Long parentId);

    Category removeProductFromCategory(Category category, Product product);

    Set<Product> getProductsForCategory(long categoryId);

    Category updateCategoryForProduct(Category productCategory, Product product);
}
