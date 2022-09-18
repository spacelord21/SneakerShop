package ru.spacelord.sneakershop.sneakershop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.domain.Category;
import ru.spacelord.sneakershop.sneakershop.domain.Product;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;
import ru.spacelord.sneakershop.sneakershop.dto.UserDTO;
import ru.spacelord.sneakershop.sneakershop.services.BucketService;
import ru.spacelord.sneakershop.sneakershop.services.CategoriesService;
import ru.spacelord.sneakershop.sneakershop.services.ProductService;

import java.util.Comparator;
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
    public String addToBucket(@RequestBody ProductDTO productDTO) {
        bucketService.saveProduct(productDTO.getId());
        return "All-good";
    }

    @PostMapping("/get-products-from-bucket")
    public List<ProductDTO> getProductsFromBucket(@RequestBody UserDTO userDTO) {
        List<ProductDTO> productDTOS = bucketService.getBucket(userDTO.getUserName());
        productDTOS.sort(Comparator.comparing(ProductDTO::getId));
        return productDTOS;
    }

    @PostMapping("/delete-product-from-bucket-{id}")
    public boolean deleteProductFromBucket(@RequestBody UserDTO userDTO, @PathVariable(value = "id") Long id) {
        return bucketService.deleteProductFromBucket(userDTO.getUserName(),id);
    }

    @PostMapping("/delete-all-from-bucket")
    public String deleteAllProductsFromBucket(@RequestBody UserDTO userDTO) {
        bucketService.deleteAllProductFromBucket(userDTO.getUserName());
        return "deleted";
    }

    @PostMapping("/delete-all-from-bucket-by-id={id}")
    public List<ProductDTO> deleteAllFromBucketById(@RequestBody UserDTO userDTO, @PathVariable(value = "id")Long id) {
        bucketService.deleteAllFromBucketById(userDTO.getUserName(),id);
        return bucketService.getBucket(userDTO.getUserName());
    }

    @PostMapping("/check-bucket")
    public Integer getAmountInBucket(@RequestBody UserDTO userDTO)  {
        return bucketService.getAmountProductsInBucket(userDTO.getUserName());
    }

}
