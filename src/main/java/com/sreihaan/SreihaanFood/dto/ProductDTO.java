package com.sreihaan.SreihaanFood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ProductDTO {

    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private boolean isNew;

    private boolean isFeatured;

    private boolean isBestSeller;

    private long categoryId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    @JsonProperty("isNew")
    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    @JsonProperty("isFeatured")
    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean featured) {
        isFeatured = featured;
    }

    @JsonProperty("isBestSeller")
    public Boolean getBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(Boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public static ProductDTO convertEntityToProductDTO(Product product)
    {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        Category category = product.getCategory();
        if(category != null)
        {
            productDTO.setCategoryId(category.getId());
        }
        return productDTO;
    }

    public static Product convertProductDTOToEntity(ProductDTO productDTO)
    {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}