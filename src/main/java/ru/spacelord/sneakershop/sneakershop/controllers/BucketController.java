package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ProductDTO addToBucket(@AuthenticationPrincipal User user, @PathVariable Long id) {
        bucketService.saveProduct(user.getUsername(), id);
        return productService.getProductById(id);
    }

    @PostMapping("/get-products-from-bucket")
    public List<ProductDTO> getProductsFromBucket(@AuthenticationPrincipal User user) {
        List<ProductDTO> productDTOS = bucketService.getBucket(user.getUsername());
        productDTOS.sort(Comparator.comparing(ProductDTO::getId));
        return productDTOS;
    }

    @PostMapping("/delete-product-from-bucket-{id}")
    public boolean deleteProductFromBucket(@AuthenticationPrincipal User user, @PathVariable(value = "id") Long id) {
        return bucketService.deleteProductFromBucket(user.getUsername(),id);
    }

    @PostMapping("/delete-all-from-bucket")
    public String deleteAllProductsFromBucket(@AuthenticationPrincipal User user) {
        bucketService.deleteAllProductFromBucket(user.getUsername());
        return "deleted";
    }

    @PostMapping("/delete-all-from-bucket-by-id={id}")
    public List<ProductDTO> deleteAllFromBucketById(@AuthenticationPrincipal User user, @PathVariable(value = "id")Long id) {
        bucketService.deleteAllFromBucketById(user.getUsername(),id);
        return bucketService.getBucket(user.getUsername());
    }

    @PostMapping("/check-bucket")
    public Integer getAmountInBucket(@AuthenticationPrincipal User user)  {
        return bucketService.getAmountProductsInBucket(user.getUsername());
    }
}
