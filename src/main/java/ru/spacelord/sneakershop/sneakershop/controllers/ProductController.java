package ru.spacelord.sneakershop.sneakershop.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.domain.Category;
import ru.spacelord.sneakershop.sneakershop.domain.Product;
import ru.spacelord.sneakershop.sneakershop.services.BucketService;
import ru.spacelord.sneakershop.sneakershop.services.CategoriesService;
import ru.spacelord.sneakershop.sneakershop.services.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final CategoriesService categoriesService;
    private final BucketService bucketService;

    @Autowired
    public ProductController(ProductService productService, CategoriesService categoriesService, BucketService bucketService) {
        this.productService = productService;
        this.categoriesService = categoriesService;
        this.bucketService = bucketService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return this.productService.getProducts();
    }
    
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return this.categoriesService.getCategories();
    }

    @PostMapping("/add-to-bucket")
    public BigDecimal addToBucket(@JsonProperty("product_id") Long id) {
        return bucketService.saveProduct(id);
    }

}
