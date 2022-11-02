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
import java.util.*;
import java.util.stream.Collectors;

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

    public Long getTotalPrice(String username) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(username).getBucket().getId());
        long sum = 0L;
        for(Product product : bucket.getProducts()) {
            sum += product.getPrice().longValue();
        }
        return sum;
    }

    public List<ProductDTO> saveProduct(String userName,Long id) {
        bucketRepository.addProduct(userRepository.findFirstByName(userName).getId(),id);
        return getFinalListProduct(userName);
    }

    public List<ProductDTO> getBucket(String userName) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        return bucket.getProducts().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDTO> deleteProductFromBucket(String userName,Long id) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        bucket.removeProduct(productRepository.findFirstById(id));
        return getFinalListProduct(userName);
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

    public List<ProductDTO> getFinalListProduct(String username) {
        List<ProductDTO> productDTOS = getBucket(username);
        HashMap<ProductDTO,Integer> dtoIntegerHashMap = new HashMap<>();
        for(ProductDTO product : productDTOS) {
            if(!dtoIntegerHashMap.containsKey(product)) {
                dtoIntegerHashMap.put(product,1);
            } else {
                dtoIntegerHashMap.put(product,dtoIntegerHashMap.get(product) + 1);
            }
        }
        List<ProductDTO> productsResult = new ArrayList<>();
        for(ProductDTO productDTO : dtoIntegerHashMap.keySet()) {
            productsResult.add(ProductDTO.builder()
                    .id(productDTO.getId())
                    .title(productDTO.getTitle())
                    .price(productDTO.getPrice())
                    .categories(productDTO.getCategories())
                    .amount(dtoIntegerHashMap.get(productDTO))
                    .build());
        }
        productsResult.sort(Comparator.comparing(ProductDTO::getId));
        return productsResult;
    }

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
    }
}
