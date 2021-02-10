package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.dto.SubCategoryDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO)
    {
        Category category = CategoryDTO.convertCategoryDTOToEntity(categoryDTO);
        category = categoryService.addCategory(category, SubCategoryDTO.convertDTOListToEntityList(categoryDTO.getSubCategory()));
        return CategoryDTO.convertEntityToCategoryDTO(category);
    }

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

    @GetMapping("/{id}/products")
    public List<ProductDTO> getCategoryProducts(@PathVariable("id") long categoryId)
    {
        List<Product> products = categoryService.getProductsForCategory(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products)
        {
            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") long categoryId)
    {
        categoryService.deleteCategory(categoryId);
    }


}
