package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.CategoryDTO;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateCategoryRequest;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping(path = "/api/category/{parentId}/subcategory")
@RestController
public class SubCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public CategoryDTO addSubCategory(@PathVariable Long parentId,
                                      @RequestBody CreateAndUpdateCategoryRequest createAndUpdateCategoryRequest)
    {
        Category subCategory = new Category();
        subCategory.setName(createAndUpdateCategoryRequest.getName());
        subCategory.setDescription(createAndUpdateCategoryRequest.getDescription());
        subCategory = categoryService.createAndAddSubCategory(parentId, subCategory);
        return CategoryDTO.convertEntityToCategoryDTO(subCategory);
    }

    @GetMapping
    public List<CategoryDTO> getSubCategories(@PathVariable Long parentId)
    {
        Set<Category> subCategories = categoryService.getSubCategories(parentId);
        return CategoryDTO.convertEntityListToDTOList(subCategories);
    }


//    @PutMapping("/{childId}")
//    public CategoryDTO updateSubCategory(@PathVariable Long parentId, @PathVariable Long childId,
//                                            @RequestBody CreateAndUpdateCategoryRequest createAndUpdateCategoryRequest)
//    {
//        Category subCategory = new Category();
//        subCategory.setName(createAndUpdateCategoryRequest.getName());
//        subCategory.setDescription(createAndUpdateCategoryRequest.getDescription());
//        subCategory = categoryService.updateSubCategoryDetails(parentId, childId, subCategory);
//        return CategoryDTO.convertEntityToCategoryDTO(subCategory);
//    }
//
//    @DeleteMapping("/{childId}")
//    public void deleteSubCategory(@PathVariable Long parentId, @PathVariable Long childId)
//    {
//        categoryService.deleteSubCategory(parentId, childId);
//    }

}
