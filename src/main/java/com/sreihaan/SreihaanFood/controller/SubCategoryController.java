package com.sreihaan.SreihaanFood.controller;


import com.sreihaan.SreihaanFood.dto.SubCategoryDTO;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/subcategory")
@RestController
public class SubCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public SubCategoryDTO addSubCategory(@RequestBody SubCategoryDTO subCategoryDTO)
    {
        SubCategory subCategory = SubCategoryDTO.convertSubCategoryDTOToEntity(subCategoryDTO);
        subCategory = categoryService.addSubCategory(subCategory, subCategoryDTO.getCategoryId());
        return SubCategoryDTO.convertEntityToSubCategoryDTO(subCategory);
    }

    @PutMapping
    public SubCategoryDTO updateSubCategory(@RequestBody SubCategoryDTO subCategoryDTO)
    {
        SubCategory subCategory = SubCategoryDTO.convertSubCategoryDTOToEntity(subCategoryDTO);
        subCategory = categoryService.updateSubCategory(subCategoryDTO.getId(), subCategory);
        return SubCategoryDTO.convertEntityToSubCategoryDTO(subCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteSubCategory(@PathVariable("id") long subCategoryId)
    {
        categoryService.deleteSubCategory(subCategoryId);
    }

}
