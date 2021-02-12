package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateCategoryRequest;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping(path = "/api/category/{parentId}/subcategory")
@RestController
public class SubCategoryController {

    @Autowired
    private CategoryService categoryService;

    private static Logger logger = LoggerFactory.getLogger(SubCategoryController.class);

    @PostMapping
    public CategoryDTO addSubCategory(@PathVariable Long parentId,
                                      @RequestBody CreateAndUpdateCategoryRequest createAndUpdateCategoryRequest)
    {
        logger.info("[Create SubCategory] create subcategory initiated, parentId -> "+parentId+" Child category name -> "+createAndUpdateCategoryRequest.getName());
        Category subCategory = new Category();
        subCategory.setName(createAndUpdateCategoryRequest.getName());
        subCategory.setDescription(createAndUpdateCategoryRequest.getDescription());
        subCategory = categoryService.createAndAddSubCategory(parentId, subCategory);
        logger.info("[Create SubCategory] subcategory creation success, subcategoryId -> "+subCategory.getId());
        return CategoryDTO.convertEntityToCategoryDTO(subCategory);
    }

    @GetMapping
    public List<CategoryDTO> getSubCategories(@PathVariable Long parentId)
    {
        Set<Category> subCategories = categoryService.getSubCategories(parentId);
        return CategoryDTO.convertEntityListToDTOList(subCategories);
    }

}
