package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.dto.ParentCategoryDTO;
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
    public List<ParentCategoryDTO> getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        List<ParentCategoryDTO> categoryDTOS = ParentCategoryDTO.convertEntityListToDTOList(categories);
        return categoryDTOS;
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CreateAndUpdateCategoryRequest createCategoryRequest)
    {
        Category category = new Category();
        category.setName(createCategoryRequest.getName());
        category.setDescription(createCategoryRequest.getDescription());
        category = categoryService.createCategory(category);
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") long categoryId, @RequestBody CreateAndUpdateCategoryRequest updateCategoryRequest)
    {
        Category category = new Category();
        category.setName(updateCategoryRequest.getName());
        category.setDescription(updateCategoryRequest.getDescription());
        category = categoryService.updateCategoryDetails(categoryId, category);
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }

//    @GetMapping("/{id}/products")
//    public List<ProductDTO> getCategoryProducts(@PathVariable("id") long categoryId) {
//        List<Product> products = categoryService.getProductsForCategory(categoryId);
//        List<ProductDTO> productDTOS = new ArrayList<>();
//        for (Product product : products) {
//            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
//            productDTOS.add(productDTO);
//        }
//        return productDTOS;
//    }

}
