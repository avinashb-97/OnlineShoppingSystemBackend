package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private long id;

    private String name;

    private String description;

    public static CategoryDTO convertEntityToCategoryDTO(Category category)
    {
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }

    public static List<CategoryDTO> convertEntityListToDTOList(Set<Category> categories) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        if(categories == null)
        {
            return categoryDTOS;
        }
        for(Category category : categories)
        {
            categoryDTOS.add(convertEntityToCategoryDTO(category));
        }
        return categoryDTOS;
    }

        public static Category convertCategoryDTOToEntity(CategoryDTO categoryDTO)
    {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        return category;
    }
}
