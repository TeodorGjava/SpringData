package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.dtos.categories.CategoryProductDTO;
import com.softuni.jsonexercises.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.jsonexercises.constants.Paths.JSON_CATEGORIES_BY_PRODUCTS_PATH;
import static com.softuni.jsonexercises.constants.Utils.writeJsonIntoFile;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductDTO> getCategoriesSummary() throws IOException {
        final List<CategoryProductDTO> categoryProductDTOS = this.categoryRepository.getCategoriesSummary()
                .orElseThrow(NoSuchElementException::new).stream().toList();

        writeJsonIntoFile(categoryProductDTOS, JSON_CATEGORIES_BY_PRODUCTS_PATH);
        return categoryProductDTOS;
    }
}
