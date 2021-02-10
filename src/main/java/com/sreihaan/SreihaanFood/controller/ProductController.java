package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateProductRequest;
import com.sreihaan.SreihaanFood.service.ImageService;
import com.sreihaan.SreihaanFood.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ProductDTO addProduct(@ModelAttribute CreateAndUpdateProductRequest createAndUpdateProductRequest)
    {
        Product product = CreateAndUpdateProductRequest.convertToProductEntity(createAndUpdateProductRequest);
        MultipartFile image = createAndUpdateProductRequest.getImage();
        product = productService.addProduct(product, createAndUpdateProductRequest.getCategoryId(), createAndUpdateProductRequest.getSubCategoryId(), image);
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = ProductDTO.convertEntityListToProductDTOList(products);
        return productDTOS;
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable("id") long id)
    {
        Product product = productService.getProductById(id);
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long productId)
    {
        Image image = productService.getImage(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getPhoto()));
    }

    @GetMapping("/bestseller")
    public List<ProductDTO> getBestSellers()
    {
        List<Product> products = productService.getBestSellers();
        return ProductDTO.convertEntityListToProductDTOList(products);
    }

    @GetMapping("/featured")
    public List<ProductDTO> getFeaturedProducts()
    {
        List<Product> products = productService.getFeaturedProducts();
        return ProductDTO.convertEntityListToProductDTOList(products);
    }


}
