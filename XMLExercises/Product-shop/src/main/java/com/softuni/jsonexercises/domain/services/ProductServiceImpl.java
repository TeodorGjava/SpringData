package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductDTO;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.jsonexercises.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.jsonexercises.constants.Paths.JSON_PRODUCTS_WITHOUT_BUYERS_PATH;
import static com.softuni.jsonexercises.constants.Utils.MODEL_MAPPER;
import static com.softuni.jsonexercises.constants.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(String low, String high) throws IOException {
        List<ProductWithoutBuyerDTO> productWithoutBuyerDTOS = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(
                        new BigDecimal(low), new BigDecimal(high))
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDTO.class))
                .map(ProductDTO::productInRangeWithoutBuyerDTO)
                .toList();
        writeJsonIntoFile(productWithoutBuyerDTOS, JSON_PRODUCTS_WITHOUT_BUYERS_PATH);
        return productWithoutBuyerDTOS;
    }
}
