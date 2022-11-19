package com.softuni.jsonexercises;

import com.softuni.jsonexercises.domain.entities.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.jsonexercises.domain.services.ProductService;
import com.softuni.jsonexercises.domain.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;

    @Autowired
    public Runner(SeedService seedService, ProductService productService) {
        this.seedService = seedService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        getAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc);
    }

    private List<ProductWithoutBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPrice(Scanner sc) throws IOException {
        return this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc.nextLine(), sc.nextLine());
    }

    private void productsInRange() {

    }
}
