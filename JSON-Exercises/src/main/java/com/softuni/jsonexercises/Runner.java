package com.softuni.jsonexercises;

import com.softuni.jsonexercises.domain.entities.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.jsonexercises.domain.services.ProductService;
import com.softuni.jsonexercises.domain.services.SeedService;
import com.softuni.jsonexercises.domain.services.UserService;
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
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public Runner(SeedService seedService, UserService userService, ProductService productService) {
        this.seedService = seedService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // getAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc);
        this.userService.
                findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName()
        ;
    }

    private List<ProductWithoutBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPrice(Scanner sc) throws IOException {
        return this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc.nextLine(), sc.nextLine());
    }

    private void productsInRange() throws IOException {
        this.userService.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName();
    }
}
