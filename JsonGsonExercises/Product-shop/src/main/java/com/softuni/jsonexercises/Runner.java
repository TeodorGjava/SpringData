package com.softuni.jsonexercises;

import com.softuni.jsonexercises.domain.entities.json.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.jsonexercises.domain.services.CategoryService;
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
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public Runner(SeedService seedService, CategoryService categoryService, UserService userService, ProductService productService) {
        this.seedService = seedService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        //this.seedService.seedUsers();
        //this.seedService.seedCategories();
        //this.seedService.seedProducts();
        // getAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc);
        // categoriesByProductsCount();
        //categoriesByProducts();
this.userService.usersAndProducts();
    }

    private void categoriesByProducts() throws IOException {
        this.categoryService.getCategoriesSummary();
    }

    private List<ProductWithoutBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPrice(Scanner sc) throws IOException {
        return this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc.nextLine(), sc.nextLine());
    }

    private void categoriesByProductsCount() throws IOException {
        this.userService.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName();
    }
}
