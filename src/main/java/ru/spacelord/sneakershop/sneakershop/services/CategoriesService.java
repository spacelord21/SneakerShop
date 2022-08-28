package ru.spacelord.sneakershop.sneakershop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spacelord.sneakershop.sneakershop.dao.CategoriesRepository;
import ru.spacelord.sneakershop.sneakershop.domain.Category;

import java.util.List;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<Category> getCategories() {
        return this.categoriesRepository.findAll();
    }
}
