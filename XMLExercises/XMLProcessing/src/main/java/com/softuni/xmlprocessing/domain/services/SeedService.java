package com.softuni.xmlprocessing.domain.services;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws FileNotFoundException, JAXBException;

    void seedCategories() throws IOException, JAXBException;

    void seedProducts() throws IOException, JAXBException;

    default void seedAll() throws IOException, JAXBException {
        seedUsers();
        seedProducts();
        seedCategories();
    }
}
