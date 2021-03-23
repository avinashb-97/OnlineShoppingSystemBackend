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
        logger.info("[Create Product] create product initiated");
        Product product = CreateAndUpdateProductRequest.convertToProductEntity(createAndUpdateProductRequest);
        MultipartFile image = createAndUpdateProductRequest.getImage();
        product = productService.addProduct(product, createAndUpdateProductRequest.getCategoryId(), createAndUpdateProductRequest.getSubCategoryId(), image);
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable("id") long productId, @ModelAttribute CreateAndUpdateProductRequest createAndUpdateProductRequest)
    {
        logger.info("[Update Product] update product initiated, Id -> "+productId);
        Product product = CreateAndUpdateProductRequest.convertToProductEntity(createAndUpdateProductRequest);
        MultipartFile image = createAndUpdateProductRequest.getImage();
        product = productService.updateProduct(productId, product, createAndUpdateProductRequest.getCategoryId(), createAndUpdateProductRequest.getSubCategoryId(), image);
        return ProductDTO.convertEntityToProductDTO(product);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts()
    {
        logger.info("[Get Products] get all products initiated");
        List<Product> products = productService.getAllProducts();
        logger.info("[Get Products] Product fetched -> size : "+products.size());
        List<ProductDTO> productDTOS = ProductDTO.convertEntityListToProductDTOList(products);
        return productDTOS;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id)
    {
        logger.info("[Delete Product] Delete product initiated, Product Id -> "+id);
        productService.removeProduct(id);
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
        logger.info("[Get Image] Get product image initiated, Product Id -> "+productId);
        Image image = productService.getImage(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename= "+image.getFilename())
                .body(new ByteArrayResource(image.getPhoto()));
    }

    @GetMapping("/bestseller")
    public List<ProductDTO> getBestSellers()
    {
        logger.info("[Get Bestseller] get bestseller products initiated");
        List<Product> products = productService.getBestSellers();
        logger.info("[Get Bestseller] bestseller products fetched, size -> "+products.size());
        return ProductDTO.convertEntityListToProductDTOList(products);
    }

    @GetMapping("/featured")
    public List<ProductDTO> getFeaturedProducts()
    {
        logger.info("[Get Featured] get featured products initiated");
        List<Product> products = productService.getFeaturedProducts();
        logger.info("[Get Featured] featured products fetched, size -> "+products.size());
        return ProductDTO.convertEntityListToProductDTOList(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String name,
                                                          @RequestParam(required = false) Integer size,
                                                          @RequestParam(required = false) Long categoryId)
    {
        int defaultSize = 50;
        size = size == null || size > defaultSize ? defaultSize : size;
        List<Product> products = productService.searchProducts(name, categoryId, size);
        List<ProductDTO> productDTOS = ProductDTO.convertEntityListToProductDTOList(products);
        return ResponseEntity.ok(productDTOS);
    }


}
