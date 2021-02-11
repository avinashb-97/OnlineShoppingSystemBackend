package com.sreihaan.SreihaanFood.service.ServiceImpl;

import com.sreihaan.SreihaanFood.exception.CategoryNotFoundException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;
import com.sreihaan.SreihaanFood.model.persistence.repository.CategoryRepository;
import com.sreihaan.SreihaanFood.model.persistence.repository.SubCategoryRepository;
import com.sreihaan.SreihaanFood.service.CategoryService;
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
    private SubCategoryRepository subCategoryRepository;

    @Override
    public Category addCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
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
    public Category updateCategory(long categoryId, Category categoryObj) {
        Category category = getCategoryById(categoryId);
        category.setName(categoryObj.getName());
        category.setDescription(categoryObj.getName());
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> getProductsForCategory(long id) {
        Category category = getCategoryById(id);
        return category.getProducts();
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = getCategoryById(categoryId);
        subCategoryRepository.deleteAll(category.getSubCategories());
        categoryRepository.delete(category);
    }

    @Override
    public SubCategory getSubCategoryById(Long subCategroyId) {
        return subCategoryRepository.findById(subCategroyId)
                .orElse(new SubCategory());
    }

    @Override
    public SubCategory addSubCategory(SubCategory subCategory, long categoryId) {
        Category category = getCategoryById(categoryId);
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        category.addSubCategory(savedSubCategory);
        categoryRepository.save(category);
        return savedSubCategory;
    }

    @Override
    public SubCategory updateSubCategory(long subCategoryId, SubCategory subCategoryObj) {
        SubCategory subCategory = getSubCategoryById(subCategoryId);
        subCategory.setName(subCategoryObj.getName());
        subCategory.setDescription(subCategoryObj.getDescription());
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public void deleteSubCategory(long subCategoryId) {
        SubCategory subCategory = getSubCategoryById(subCategoryId);
        Category category = subCategory.getCategory();
        category.removeSubCategory(subCategory);
        categoryRepository.save(category);
        subCategoryRepository.delete(subCategory);
    }
}
