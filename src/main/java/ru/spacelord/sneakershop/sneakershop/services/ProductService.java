package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spacelord.sneakershop.sneakershop.dao.ProductRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Product;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

}
