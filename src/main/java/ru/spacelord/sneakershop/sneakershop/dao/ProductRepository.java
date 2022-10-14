package ru.spacelord.sneakershop.sneakershop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.domain.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product getProductById(Long id);
    void deleteById(Long id);
    Product findFirstById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products set title=:title, price=:price where id=:id",nativeQuery = true)
    void changeProduct(@Param("id") Long id, @Param("title") String title, @Param("price") BigDecimal price);

    @Transactional
    @Modifying
    @Query(value = "insert into products (id,price,title) values (:product_id,:product_price,:product_title)", nativeQuery = true)
    void addProduct(@Param("product_price") BigDecimal price,
                    @Param("product_title") String title,
                    @Param("product_id") Long id);
    @Transactional
    @Modifying
    @Query(value = "insert into products_categories(product_id, category_id) VALUES (:product_id,:category_id)", nativeQuery = true)
    void addCategoryForProduct(@Param("product_id") Long productId, @Param("category_id") Long categoryId);


}
