package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryDTO extends CategoryDTO{

    private Set<CategoryDTO> subCategory = new HashSet<>();

    public static ParentCategoryDTO convertEntityToParentCategoryDTO(Category category) {
        ParentCategoryDTO categoryDTO = new ParentCategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        Set<Category> subCategories = category.getChildCategories();
        for (Category subCategory : subCategories)
        {
            categoryDTO.getSubCategory().add(new CategoryDTO(subCategory.getId(), subCategory.getName(), subCategory.getDescription()));
        }
        return categoryDTO;
    }

    public static List<ParentCategoryDTO> convertEntityListToDTOList(List<Category> categories)
    {
        List<ParentCategoryDTO> parentCategoryDTOS = new ArrayList<>();
        for(Category category : categories)
        {
            if(category.getParent() == null)
            {
                parentCategoryDTOS.add(convertEntityToParentCategoryDTO(category));
            }
        }
        return parentCategoryDTOS;
    }
}
