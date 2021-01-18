package com.sreihaan.SreihaanFood.controller;

import com.sreihaan.SreihaanFood.dto.ProductDTO;
import com.sreihaan.SreihaanFood.model.persistence.Image;
import com.sreihaan.SreihaanFood.model.persistence.Product;
import com.sreihaan.SreihaanFood.model.requests.CreateAndUpdateProductRequest;
import com.sreihaan.SreihaanFood.service.ImageService;
import com.sreihaan.SreihaanFood.service.ProductService;
import com.sreihaan.SreihaanFood.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ProductDTO addProduct(@ModelAttribute CreateAndUpdateProductRequest createAndUpdateProductRequest)
    {
        Product product = CreateAndUpdateProductRequest.convertToProductEntity(createAndUpdateProductRequest);
        MultipartFile image = createAndUpdateProductRequest.getImage();
        product = productService.addProduct(product, createAndUpdateProductRequest.getCategoryId(), image);
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products)
        {
            ProductDTO productDTO = ProductDTO.convertEntityToProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable("id") long id)
    {
        return null;
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("id") long productId)
    {
        Image image = productService.getImage(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getData()));
    }
}
