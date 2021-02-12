package com.sreihaan.SreihaanFood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.utils.ProductUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long id;

    private String name;

    private String code;

    private String productSize;

    private String cartonSize;

    private long pcsPerBag;

    private long bagsPerCtn;

    private long pcsPerCtn;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    @JsonProperty("isNew")
    private Boolean isNew;

    @JsonProperty("isFeatured")
    private Boolean isFeatured;

    @JsonProperty("isBestSeller")
    private Boolean isBestSeller;

    private Long subCategoryId;

    private String subCategory;

    private long categoryId;

    private String category;

    private String imageUrl;

    public static Product convertProductDTOToEntity(ProductDTO productDTO)
    {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }

    public static ProductDTO convertEntityToProductDTO(Product product)
    {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        Category category = product.getCategory();
        Category parent = category.getParent();
        if(category != null && parent == null)
        {
            productDTO.setCategoryId(category.getId());
            productDTO.setCategory(category.getName());
            productDTO.setSubCategoryId(null);
            productDTO.setSubCategory(null);
        }
        else if(category != null && parent != null)
        {
            productDTO.setCategoryId(parent.getId());
            productDTO.setCategory(parent.getName());
            productDTO.setSubCategoryId(category.getId());
            productDTO.setSubCategory(category.getName());
        }
        Image image = product.getImage();
        String imageUrl = ProductUtil.getImageUrl(image, product.getId());
        productDTO.setImageUrl(imageUrl);
        return productDTO;
    }

    public static List<ProductDTO> convertEntityListToProductDTOList(List<Product> productList)
    {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : productList)
        {
            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

}
