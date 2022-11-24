package com.softuni.jsonexercises.constants;

import java.nio.file.Path;

public enum JsonPaths {
    ;
    public static final Path JSON_USERS_PATH
            = Path.of("src", "main", "resources", "jsons/dbContent", "users.json");
    public static final Path JSON_CATEGORIES_PATH
            = Path.of("src", "main", "resources", "jsons/dbContent", "categories.json");
    public static final Path JSON_PRODUCTS_PATH = Path.of("src", "main", "resources", "jsons/dbContent", "products.json");
    public static final Path JSON_PRODUCTS_WITHOUT_BUYERS_PATH =
            Path.of("src", "main", "resources", "output","json", "products-in-range.json");
    public static final Path JSON_SOLD_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output","json", "successfully-sold-products.json");
    public static final Path JSON_CATEGORIES_BY_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output","json", "categories-by-products.json");
    public static final Path JSON_USERS_AND_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output","json", "users-and-products.json");
}
