package ru.spacelord.sneakershop.sneakershop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.domain.Bucket;

import java.math.BigDecimal;


@Repository
public interface BucketRepository extends JpaRepository<Bucket,Long> {


    Bucket findFirstById(Long id);

    @Transactional
    @Modifying
    @Query(value = "insert into buckets_products(bucket_id,product_id) values(:bucketId,:productId)",nativeQuery = true)
    void addProduct(@Param("bucketId") Long b_id,@Param("productId") Long p_id);

    @Transactional
    @Modifying
    @Query(value = "update buckets set sum=:summa where id=:id", nativeQuery = true)
    void updateSum(@Param("summa") BigDecimal summa, @Param("id") Long id);
}
