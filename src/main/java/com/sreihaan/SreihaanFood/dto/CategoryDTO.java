package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDTO {

    private long id;

    private String name;

    private String description;

    private List<SubCategoryDTO> subCategory;

    public static CategoryDTO convertEntityToCategoryDTO(Category category)
    {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        List<SubCategoryDTO> subCategoryDTOS = SubCategoryDTO.convertEntityListToDTOList(category.getSubCategories());
        categoryDTO.setSubCategory(subCategoryDTOS);
        return categoryDTO;
    }

    public static Category convertCategoryDTOToEntity(CategoryDTO categoryDTO)
    {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        return category;
    }
}
