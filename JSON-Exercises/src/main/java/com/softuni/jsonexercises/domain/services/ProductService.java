package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductWithoutBuyerDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(String low, String high) throws IOException;
}
