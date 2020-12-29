package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
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

    @PostMapping
    public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO)
    {
        Category category = CategoryDTO.convertCategoryDTOToEntity(categoryDTO);
        category = categoryService.addCategory(category);
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

}
