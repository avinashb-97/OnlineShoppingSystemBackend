package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.exception.InvalidCategoryException;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CategoryUtil {

    @Autowired
    private CategoryService categoryService;

    public static boolean isChildCategory(Category category, Category parent) {
        return category.getParent() != null && category.getParent().equals(parent);
    }

    public static void checkIsParentCategory(Category category)
    {
        if(category.getParent() != null)
        {
            throw new InvalidCategoryException("category " + category.getId() + " is not a parent category");
        }
    }

    public static void checkIsParentAndChildCategory(Category child, Category parent) {
        checkIsParentCategory(parent);
        if(child == null)
        {
            return;
        }
        else if(!isChildCategory(child, parent))
        {
            throw new InvalidCategoryException("category " + parent.getId() + " is not parent of " + child.getId());
        }
    }

}
