package com.softuni.xmlprocessing;


import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductWithoutBuyerDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.users.UserWithProductsDTO;
import com.softuni.xmlprocessing.domain.services.CategoryService;
import com.softuni.xmlprocessing.domain.services.ProductService;
import com.softuni.xmlprocessing.domain.services.SeedService;
import com.softuni.xmlprocessing.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
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
     // addUsersToDatabase();
       //addCategoriesToDatabase();
       // addProductsWithRandomSellerAndBuyerToDatabase();
       //getAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc);

        this.userService.usersSoldProducts();
        // categoriesByProductsCount();
        //categoriesByProducts();
        //this.userService.usersAndProducts();
    }

    private void addProductsWithRandomSellerAndBuyerToDatabase() throws IOException, JAXBException {
        this.seedService.seedProducts();
    }

    private void addUsersToDatabase() throws FileNotFoundException, JAXBException {
        this.seedService.seedUsers();
    }

    private void addCategoriesToDatabase() throws IOException, JAXBException {
        this.seedService.seedCategories();
    }

    private void categoriesByProducts() throws IOException {
        this.categoryService.getCategoriesSummary();
    }

    private List<ProductWithoutBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPrice(Scanner sc) throws IOException, JAXBException {
        return this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(sc.nextLine(), sc.nextLine());
    }

    private void categoriesByProductsCount() throws IOException {
        this.userService.findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName();
    }
}
