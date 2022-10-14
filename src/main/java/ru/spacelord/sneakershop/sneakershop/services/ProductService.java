package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.dao.ProductRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Category;
import ru.spacelord.sneakershop.sneakershop.domain.Product;
import ru.spacelord.sneakershop.sneakershop.dto.ProductDTO;

import java.util.ArrayList;
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

    @Transactional
    public boolean changeProduct(ProductDTO productDTO) {
        Product product = productRepository.getProductById(productDTO.getId());
        if(product.getCategories().size()!=productDTO.getCategories().size()) {
            List<Category> categories = new ArrayList<>(productDTO.getCategories().size()>product.getCategories().size() ? product.getCategories() : productDTO.getCategories());
            for(Category category : productDTO.getCategories().size()>product.getCategories().size() ? productDTO.getCategories() : product.getCategories()) {
                if(!categories.contains(category)) {
                    try {
                        productRepository.addCategoryForProduct(productDTO.getId(), category.getId());
                    }catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        try {
            productRepository.changeProduct(productDTO.getId(),productDTO.getTitle(),productDTO.getPrice());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean createNewProduct(ProductDTO productDTO) {
        try {
            productRepository.addProduct(productDTO.getPrice(),productDTO.getTitle(),productDTO.getId());
            productDTO.getCategories().forEach(category -> productRepository.addCategoryForProduct(productDTO.getId(), category.getId()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
           return false;
        }
        return true;
    }

}
