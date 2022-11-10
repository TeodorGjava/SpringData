package com.softuni.springbootintroexercises.domain.services.seed;

import java.io.IOException;

public interface SeedService {

    void seedAuthors() throws IOException;

    void seedBooks() throws IOException;

    void seedCategory() throws IOException;

    default void seedAllData() throws IOException {
        seedBooks();
        seedCategory();
        seedAuthors();

    }
}
