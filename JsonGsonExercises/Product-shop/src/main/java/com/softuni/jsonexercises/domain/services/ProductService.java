package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.json.dtos.products.ProductWithoutBuyerDTO;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(String low, String high) throws IOException;
}
