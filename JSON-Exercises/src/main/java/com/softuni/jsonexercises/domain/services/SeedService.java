package com.softuni.jsonexercises.domain.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;

    void seedCategories();

    void seedProducts();

    default void seedAll() throws FileNotFoundException {
        seedUsers();
        seedProducts();
        seedCategories();
    }
}
