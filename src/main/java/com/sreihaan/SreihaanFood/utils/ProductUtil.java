package com.sreihaan.SreihaanFood.utils;

import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.beans.BeanUtils;

public class ProductUtil {

    public static final String imageUrl = "/api/product/{id}/image";

    public static String getImageUrl(Image image, Long productId)
    {
        String baseUrl = AuthUtil.getBaseUrl();
        try
        {
            long size = image.getFileSize();
            if(size > 0)
            {
                String url = baseUrl+imageUrl;
                url = url.replace("{id}", productId.toString());
                return url;
            }
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    public static Product copyPropertiesForUpdate(Product source, Product target)
    {
        Category category = target.getCategory();
        BeanUtils.copyProperties(source, target);
        target.setCategory(category);
        return target;
    }
}
