package com.softuni.cardealer.domain.services;

import java.io.FileNotFoundException;

public interface SeedService {

    void seedSuppliers() throws FileNotFoundException;
    void seedParts() throws FileNotFoundException;
    void seedCars() throws FileNotFoundException;
    void seedCustomers();
}
