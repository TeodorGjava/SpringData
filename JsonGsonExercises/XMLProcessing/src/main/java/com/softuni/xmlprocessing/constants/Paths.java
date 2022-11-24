package com.softuni.xmlprocessing.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path XML_USERS_PATH
            = Path.of("src", "main", "resources", "dbContent", "users.xml");
    public static final Path XML_CATEGORIES_PATH
            = Path.of("src", "main", "resources", "dbContent", "categories.xml");
    public static final Path XML_PRODUCTS_PATH = Path.of("src", "main", "resources", "dbContent", "products.xml");
    public static final Path XML_PRODUCTS_WITHOUT_BUYERS_PATH = Path.of("src", "main", "resources", "output", "products-in-range.xml");
    public static final Path XML_USERS_WITH_SOLD_PRODUCTS = Path.of("src", "main", "resources", "output", "users-sold-products.xml");
    public static final Path XML_SOLD_PRODUCTS_PATH = Path.of("src", "main", "resources", "output", "successfully-sold-products.xml");
    public static final Path XML_CATEGORIES_BY_PRODUCTS_PATH = Path.of("src", "main", "resources", "output", "categories-by-products.xml");
    public static final Path XML_USERS_AND_PRODUCTS_PATH = Path.of("src", "main", "resources", "output", "users-and-products.xml");
}
