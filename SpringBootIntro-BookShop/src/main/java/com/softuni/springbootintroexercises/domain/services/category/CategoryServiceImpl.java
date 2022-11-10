package com.softuni.springbootintroexercises.domain.services.category;

import com.softuni.springbootintroexercises.domain.entities.Category;
import com.softuni.springbootintroexercises.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(List<Category> categories) {
        this.categoryRepository.saveAll(categories);
    }

    @Override
    public boolean isDataSeeded() {
        return this.categoryRepository.count() > 0;
    }

    @Override
    public Set<Category> getRandomCategories() {
        long count = this.categoryRepository.count();
        if (count != 0) {
            long randomAuthorId = new Random().nextLong(1L, count) + 1L;
            return Set.of(this.categoryRepository.findById(randomAuthorId).orElseThrow(NoSuchElementException::new));
        }
        throw new RuntimeException("Error");
    }
}
