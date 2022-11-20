package com.softuni.cardealer.domain.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path JSON_USERS_PATH
            = Path.of("src", "main", "resources", "dbContent", "users.json");
    public static final Path JSON_CATEGORIES_PATH
            = Path.of("src", "main", "resources", "dbContent", "categories.json");
    public static final Path JSON_SUPPLIERS_PATH = Path.of("src", "main", "resources", "dbContent", "suppliers.json");
    public static final Path JSON_PARTS_PATH = Path.of("src", "main", "resources", "dbContent", "parts.json");
    public static final Path JSON_CARS_PATH = Path.of("src", "main", "resources", "dbContent", "cars.json");
    public static final Path JSON_CUSTOMERS_PATH = Path.of("src", "main", "resources", "dbContent", "customers.json");
    public static final Path JSON_USERS_AND_PRODUCTS_PATH = Path.of("src", "main", "resources", "output", "users-and-products.json");
}
