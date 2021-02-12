package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.dto.ParentCategoryDTO;
import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateCategoryRequest;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping
    public List<ParentCategoryDTO> getAllCategories()
    {
        logger.info("[Get All Categories] Get all categories initiated");
        List<Category> categories = categoryService.getAllCategories();
        List<ParentCategoryDTO> categoryDTOS = ParentCategoryDTO.convertEntityListToDTOList(categories);
        return categoryDTOS;
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CreateAndUpdateCategoryRequest createCategoryRequest)
    {
        logger.info("[Create Category] Create category initiated, category name : "+ createCategoryRequest.getName());
        Category category = new Category();
        category.setName(createCategoryRequest.getName());
        category.setDescription(createCategoryRequest.getDescription());
        category = categoryService.createCategory(category);
        logger.info("[Create Category] Create category success, category id : "+ createCategoryRequest.getId());
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable("id") long categoryId, @RequestBody CreateAndUpdateCategoryRequest updateCategoryRequest)
    {
        logger.info("[Update Category] Update category initiated, category id: "+ categoryId);
        Category category = new Category();
        category.setName(updateCategoryRequest.getName());
        category.setDescription(updateCategoryRequest.getDescription());
        category = categoryService.updateCategoryDetails(categoryId, category);
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long categoryId)
    {
        logger.info("[Delete Category] Delete category initiated, category id: "+ categoryId);
        categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/{id}/products")
    public List<ProductDTO> getCategoryProducts(@PathVariable("id") long categoryId) {
        logger.info("[Get Category Products] Get category initiated, category id: "+ categoryId);
        Set<Product> products = categoryService.getProductsForCategory(categoryId);
        logger.info("[Get Category Products] Category products fetched, size -> "+products.size());
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

}
