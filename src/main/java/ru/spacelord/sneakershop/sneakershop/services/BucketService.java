package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spacelord.sneakershop.sneakershop.dao.BucketRepository;
import ru.spacelord.sneakershop.sneakershop.dao.ProductRepository;


import java.math.BigDecimal;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;


    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductRepository productRepository) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
    }

    public BigDecimal saveProduct(Long id) {
        bucketRepository.addProduct(1L,id);
        BigDecimal sum = bucketRepository.findFirstById(1L).getSum();
        sum = sum.add(productRepository.getProductById(id).getPrice());
        bucketRepository.updateSum(sum,1L);
        return sum;
    }


}
