package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.User;
import com.softuni.jsonexercises.domain.entities.dtos.ImportUserDTO;
import com.softuni.jsonexercises.domain.repositories.CategoryRepository;
import com.softuni.jsonexercises.domain.repositories.ProductRepository;
import com.softuni.jsonexercises.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static com.softuni.jsonexercises.constants.Paths.JSON_USERS_PATH;
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
            this.userRepository.saveAll(users);
        }
    }

    @Override
    public void seedCategories() {
    }

    @Override
    public void seedProducts() {
    }

}
