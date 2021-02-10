package com.sreihaan.SreihaanFood.dto;

import com.sreihaan.SreihaanFood.model.persistence.SubCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubCategoryDTO {

    private long id;

    private String name;

    private String description;

    public static SubCategory convertSubCategoryDTOToEntity(SubCategoryDTO subCategoryDTO)
    {
        SubCategory subCategory = new SubCategory();
        BeanUtils.copyProperties(subCategoryDTO, subCategory);
        return subCategory;
    }
    public static SubCategoryDTO convertEntityToSubCategoryDTO(SubCategory subCategory)
    {
        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
        BeanUtils.copyProperties(subCategory, subCategoryDTO);
        return subCategoryDTO;
    }

    public static List<SubCategory> convertDTOListToEntityList(List<SubCategoryDTO> subCategoryDTOList)
    {
        List<SubCategory> subCategoryList = new ArrayList<>();
        for(SubCategoryDTO subCategoryDTO : subCategoryDTOList)
        {
            subCategoryList.add(convertSubCategoryDTOToEntity(subCategoryDTO));
        }
        return subCategoryList;
    }

    public static List<SubCategoryDTO> convertEntityListToDTOList(List<SubCategory> subCategories)
    {
        List<SubCategoryDTO> subCategoryDTOSList = new ArrayList<>();
        for(SubCategory subCategory : subCategories)
        {
            subCategoryDTOSList.add(convertEntityToSubCategoryDTO(subCategory));
        }
        return subCategoryDTOSList;
    }

}
