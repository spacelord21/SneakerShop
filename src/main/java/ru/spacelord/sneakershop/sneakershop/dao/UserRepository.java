package ru.spacelord.sneakershop.sneakershop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spacelord.sneakershop.sneakershop.domain.Bucket;
import ru.spacelord.sneakershop.sneakershop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByName(String name);
    User findByEmail(String email);
    User findFirstById(Long id);


    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update User set bucket=:bucket where id=:id")
    void addBucketToUser(Bucket bucket, Long id);

}
