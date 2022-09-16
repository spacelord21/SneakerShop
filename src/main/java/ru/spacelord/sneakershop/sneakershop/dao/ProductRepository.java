package ru.spacelord.sneakershop.sneakershop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spacelord.sneakershop.sneakershop.domain.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product getProductById(Long id);
    Product findFirstById(Long id);
}
