package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.dao.BucketRepository;
import ru.spacelord.sneakershop.sneakershop.dao.ProductRepository;
import ru.spacelord.sneakershop.sneakershop.dao.UserRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Bucket;
import ru.spacelord.sneakershop.sneakershop.domain.Product;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Autowired
    public BucketService(BucketRepository bucketRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void saveProduct(Long id) {
        bucketRepository.addProduct(1L,id);
    }

    public List<ProductDTO> getBucket(String userName) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        List<Product> products = bucket.getProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        Map<Product,Integer> map = new HashMap<>();
        for(Product product : products) {
            if(!map.containsKey(product)) {
                map.put(product,1);
            } else {
                map.put(product,map.get(product) + 1);
            }
        }
        for(Product product : map.keySet()) {
            productDTOS.add(ProductDTO.builder()
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .id(product.getId())
                    .categories(product.getCategories())
                    .amount(map.get(product))
                    .build());
        }
        return productDTOS;
    }


    @Transactional
    public boolean deleteProductFromBucket(String userName,Long id) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        Product product = productRepository.findFirstById(id);
        System.out.println(product);
        return bucket.removeProduct(product);
//        bucket.removeProduct(product);
    }

    @Transactional
    public void deleteAllProductFromBucket(String userName) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        bucket.deleteAll();
    }

    @Transactional
    public void deleteAllFromBucketById(String userName, Long id) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        bucket.deleteAllById(id);
    }

}
