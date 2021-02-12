package com.sreihaan.SreihaanFood.model.requests;

import com.sreihaan.SreihaanFood.model.persistence.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAndUpdateProductRequest {

    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private boolean isNew;

    private boolean isFeatured;

    private boolean isBestSeller;

    private String code;

    private String productSize;

    private String cartonSize;

    private long pcsPerBag;

    private long bagsPerCtn;

    private long pcsPerCtn;

    private Long categoryId;

    private Long subCategoryId;

    private MultipartFile image;

    public static Product convertToProductEntity(CreateAndUpdateProductRequest createAndUpdateProductRequest)
    {
        Product product = new Product();
        BeanUtils.copyProperties(createAndUpdateProductRequest, product);
        return product;
    }

}
