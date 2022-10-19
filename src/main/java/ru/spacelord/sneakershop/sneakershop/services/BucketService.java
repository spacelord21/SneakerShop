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


import java.util.*;

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

    public void saveProduct(String userName,Long id) {
        bucketRepository.addProduct(userRepository.findFirstByName(userName).getId(),id);
    }

    public List<ProductDTO> getBucket(String userName) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        return getProductsWithTotalAmount(bucket);
    }

    public Integer getAmountProductsInBucket(String userName) {
        return bucketRepository
                .getBucketById(userRepository.findFirstByName(userName).getBucket().getId())
                .getProducts().size();
    }


    @Transactional
    public ProductDTO deleteProductFromBucket(String userName,Long id) {
        Bucket bucket = bucketRepository.getBucketById(userRepository.findFirstByName(userName).getBucket().getId());
        bucket.removeProduct(productRepository.findFirstById(id));
        //ProductDTO productDTO = getProductsWithTotalAmount(bucket).stream().filter(item -> !Objects.equals(item.getId(), id))
        // доделать
        return null;
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


    public List<ProductDTO> getProductsWithTotalAmount(Bucket bucket) {
        List<Product> products = bucket.getProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        HashMap<Product,Integer> map = productsMap(products);
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

    public HashMap<Product,Integer> productsMap(List<Product> products) {
        HashMap<Product,Integer> map = new HashMap<>();
        for(Product product : products) {
            if(!map.containsKey(product)) {
                map.put(product,1);
            } else {
                map.put(product,map.get(product) + 1);
            }
        }
        return map;
    }
}
