package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO)
    {
        Product product = ProductDTO.convertProductDTOToEntity(productDTO);
        product = productService.addProduct(product, productDTO.getCategoryId());
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products)
        {
            productDTOS.add(ProductDTO.convertEntityToProductDTO(product));
        }
        return productDTOS;
    }

}
