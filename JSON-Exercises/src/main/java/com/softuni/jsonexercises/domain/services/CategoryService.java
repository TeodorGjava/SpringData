package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.dtos.categories.CategoryProductDTO;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryProductDTO> getCategoriesSummary() throws IOException;
}
