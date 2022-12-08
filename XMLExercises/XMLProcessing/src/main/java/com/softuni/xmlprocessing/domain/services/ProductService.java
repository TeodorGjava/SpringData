package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductWithoutBuyerDTO;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(String low, String high) throws IOException, JAXBException;
}
