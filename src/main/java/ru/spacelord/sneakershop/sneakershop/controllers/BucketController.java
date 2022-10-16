package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;
import ru.spacelord.sneakershop.sneakershop.services.BucketService;
import ru.spacelord.sneakershop.sneakershop.services.ProductService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {

    private final BucketService bucketService;
    private final ProductService productService;

    @Autowired
    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }

    @PostMapping("/add-to-bucket={id}")
    public ProductDTO addToBucket(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bucketService.saveProduct(username, id);
        return productService.getProductById(id);
    }

    @PostMapping("/get-products-from-bucket")
    public List<ProductDTO> getProductsFromBucket() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ProductDTO> productDTOS = bucketService.getBucket(username);
        productDTOS.sort(Comparator.comparing(ProductDTO::getId));
        return productDTOS;
    }

    @PostMapping("/delete-product-from-bucket-{id}")
    public boolean deleteProductFromBucket(@PathVariable(value = "id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bucketService.deleteProductFromBucket(username,id);
    }

    @PostMapping("/delete-all-from-bucket")
    public String deleteAllProductsFromBucket() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bucketService.deleteAllProductFromBucket(username);
        return "deleted";
    }

    @PostMapping("/delete-all-from-bucket-by-id={id}")
    public List<ProductDTO> deleteAllFromBucketById(@PathVariable(value = "id")Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        bucketService.deleteAllFromBucketById(username,id);
        return bucketService.getBucket(username);
    }

    @PostMapping("/check-bucket")
    public Integer getAmountInBucket()  {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bucketService.getAmountProductsInBucket(username);
    }
}
