package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateCategoryRequest;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories)
        {
            categoryDTOS.add(CategoryDTO.convertEntityToCategoryDTO(category));
        }
        return categoryDTOS;
    }

    @PostMapping
    public CategoryDTO addCategory(@RequestBody CreateAndUpdateCategoryRequest createCategoryRequest)
    {
        Category category = new Category();
        category.setName(createCategoryRequest.getName());
        category.setDescription(createCategoryRequest.getDescription());
        category = categoryService.addCategory(category);
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @PutMapping
    public CategoryDTO updateCategory(@RequestBody CreateAndUpdateCategoryRequest updateCategoryRequest)
    {
        Category category = new Category();
        category.setName(updateCategoryRequest.getName());
        category.setDescription(updateCategoryRequest.getDescription());
        category = categoryService.updateCategory(updateCategoryRequest.getId(), category);
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/{id}/products")
    public List<ProductDTO> getCategoryProducts(@PathVariable("id") long categoryId) {
        List<Product> products = categoryService.getProductsForCategory(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

}
