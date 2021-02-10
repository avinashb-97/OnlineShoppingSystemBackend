package com.sreihaan.SreihaanFood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sreihaan.SreihaanFood.model.persistence.Category;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.persistence.SubCategory;
import com.sreihaan.SreihaanFood.utils.ProductUtil;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    private boolean isNew;

    private boolean isFeatured;

    private boolean isBestSeller;

    private long subCategoryId;

    private String subCategory;

    private long categoryId;

    private String category;

    private String imageUrl;

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
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @JsonProperty("isFeatured")
    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    @JsonProperty("isBestSeller")
    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getCartonSize() {
        return cartonSize;
    }

    public void setCartonSize(String cartonSize) {
        this.cartonSize = cartonSize;
    }

    public long getPcsPerBag() {
        return pcsPerBag;
    }

    public void setPcsPerBag(long pcsPerBag) {
        this.pcsPerBag = pcsPerBag;
    }

    public long getBagsPerCtn() {
        return bagsPerCtn;
    }

    public void setBagsPerCtn(long bagsPerCtn) {
        this.bagsPerCtn = bagsPerCtn;
    }

    public long getPcsPerCtn() {
        return pcsPerCtn;
    }

    public void setPcsPerCtn(long pcsPerCtn) {
        this.pcsPerCtn = pcsPerCtn;
    }

    public long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

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
        if(category != null)
        {
            productDTO.setCategoryId(category.getId());
            productDTO.setCategory(category.getName());
        }
        SubCategory subCategory = product.getSubCategory();
        if(subCategory != null)
        {
            productDTO.setSubCategory(subCategory.getName());
            productDTO.setSubCategoryId(subCategory.getId());
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
