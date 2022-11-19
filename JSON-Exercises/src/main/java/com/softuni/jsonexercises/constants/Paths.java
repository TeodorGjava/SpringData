package com.softuni.jsonexercises.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path JSON_USERS_PATH
            = Path.of("src", "main", "resources", "dbContent", "users.json");
    public static final Path JSON_CATEGORIES_PATH
            = Path.of("src", "main", "resources", "dbContent", "categories.json");
    public static final Path JSON_PRODUCTS_PATH
            = Path.of("src", "main", "resources", "dbContent", "products.json");
}
