package ru.spacelord.sneakershop.sneakershop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spacelord.sneakershop.sneakershop.domain.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

}
