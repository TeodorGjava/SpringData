package com.softuni.jsonexercises.domain.services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException;

    void seedCategories() throws IOException;

    void seedProducts() throws IOException;

    default void seedAll() throws IOException {
        seedUsers();
        seedProducts();
        seedCategories();
    }
}
