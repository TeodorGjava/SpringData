package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductInPriceRangeWrapperDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.xmlprocessing.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.xmlprocessing.constants.Paths.XML_PRODUCTS_WITHOUT_BUYERS_PATH;
import static com.softuni.xmlprocessing.constants.Utils.MODEL_MAPPER;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductWithoutBuyerDTO> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(String low, String high) throws IOException, JAXBException {

        final List<ProductWithoutBuyerDTO> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(
                        new BigDecimal(low), new BigDecimal(high))
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDTO.class))
                .map(ProductDTO::productInRangeWithoutBuyerDTO)
                .toList();
        final ProductInPriceRangeWrapperDTO productsWrapper = new ProductInPriceRangeWrapperDTO(products);

        final JAXBContext context = JAXBContext.newInstance(ProductInPriceRangeWrapperDTO.class);
        final Marshaller marshaller = context.createMarshaller();

        final File file = XML_PRODUCTS_WITHOUT_BUYERS_PATH.toFile();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT
                , Boolean.TRUE);
        marshaller.marshal(productsWrapper, file);
        return products;
    }
}
