package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.Category;
import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.User;
import com.softuni.jsonexercises.domain.entities.dtos.categories.CategoryImportDTO;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductImportDTO;
import com.softuni.jsonexercises.domain.entities.dtos.users.ImportUserDTO;
import com.softuni.jsonexercises.domain.repositories.CategoryRepository;
import com.softuni.jsonexercises.domain.repositories.ProductRepository;
import com.softuni.jsonexercises.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static com.softuni.jsonexercises.constants.Paths.*;
import static com.softuni.jsonexercises.constants.Utils.GSON;
import static com.softuni.jsonexercises.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_USERS_PATH.toFile());
            List<User> users = Arrays.stream(GSON.fromJson(fileReader, ImportUserDTO[].class))
                    .map(userDTo -> MODEL_MAPPER.map(userDTo, User.class)).toList();
            this.userRepository.saveAllAndFlush(users);
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_CATEGORIES_PATH.toFile());
            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDTO[].class))
                    .map(categoryImportDTO -> MODEL_MAPPER.map(categoryImportDTO, Category.class)).toList();
            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_PRODUCTS_PATH.toFile());
            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDTO[].class))
                    .map(productImportDTO -> MODEL_MAPPER.map(productImportDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .toList();
            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomCategory(Product product) {
        Category category = this.categoryRepository.getRandomCategory().orElseThrow(NoSuchElementException::new);
        Random random = new Random();
        Long highEnd = this.categoryRepository.count();
        int numberOfCategories = random.nextInt(highEnd.intValue());
        Set<Category> categories = new HashSet<>();

        IntStream.rangeClosed(1, numberOfCategories)
                .forEach(num -> {
                    this.categoryRepository.getRandomCategory()
                            .orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(new BigDecimal(800L)) > 0) {
            User buyer = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
            while (!buyer.equals(product.getSeller())) {
                buyer = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
            }
            product.setBuyer(buyer);

        }
        return product;
    }

    private Product setRandomSeller(Product product) {
        final User user = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
        product.setSeller(user);
        return product;
    }

}
