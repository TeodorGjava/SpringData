package com.softuni.xmlprocessing.domain.services;

import com.softuni.xmlprocessing.domain.entities.dtos.categories.CategoryProductDTO;
import com.softuni.xmlprocessing.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;


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


        return categoryProductDTOS;
    }
}
