package ru.spacelord.sneakershop.sneakershop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spacelord.sneakershop.sneakershop.domain.Product;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;
import ru.spacelord.sneakershop.sneakershop.services.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return this.productService.getProducts();
    }

    @PostMapping("/set-products/change-product")
    public boolean setProduct(@RequestBody ProductDTO productDTO) {
        return productService.changeProduct(productDTO);
    }

    @PostMapping("/set-products/add-new")
    public boolean addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.createNewProduct(productDTO);
    }

    @PostMapping("/set-products/delete={id}")
    public boolean deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
