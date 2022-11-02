package ru.spacelord.sneakershop.sneakershop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;
import ru.spacelord.sneakershop.sneakershop.services.BucketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bucket")
public class BucketController {

    private final BucketService bucketService;

    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("/add-to-bucket={id}")
    public List<ProductDTO> addToBucket(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bucketService.saveProduct(username, id);
    }

    @PostMapping("/get-products-from-bucket")
    public List<ProductDTO> getProductsFromBucket() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bucketService.getFinalListProduct(username);
    }

    @PostMapping("/delete-product-from-bucket-{id}")
    public List<ProductDTO> deleteProductFromBucket(@PathVariable(value = "id") Long id) {
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
        return bucketService.getFinalListProduct(username);
    }

    @PostMapping("/get-total-price-products-in-bucket")
    public Long getTotalPriceProductsInBucket() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bucketService.getTotalPrice(username);
    }

}
