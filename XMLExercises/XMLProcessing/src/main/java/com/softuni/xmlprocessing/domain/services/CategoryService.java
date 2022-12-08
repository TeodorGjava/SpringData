package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.categories.CategoryProductDTO;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryProductDTO> getCategoriesSummary() throws IOException;
}
