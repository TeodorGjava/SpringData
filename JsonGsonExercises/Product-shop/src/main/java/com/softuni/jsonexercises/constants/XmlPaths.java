package com.softuni.jsonexercises.constants;

import java.nio.file.Path;

public class XmlPaths {
    public static final Path XML_USERS_PATH =
            Path.of("src", "main", "resources", "xmls", "users.xml");
    public static final Path XML_CATEGORIES_PATH
            = Path.of("src", "main", "resources", "xmls", "categories.xml");
    public static final Path XML_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "xmls", "products.xml");
    public static final Path XML_PRODUCTS_WITHOUT_BUYERS_PATH =
            Path.of("src", "main", "resources", "output", "xml", "products-in-range.xml");
    public static final Path XML_SOLD_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output", "xml", "successfully-sold-products.xml");
    public static final Path XML_CATEGORIES_BY_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output", "xml", "categories-by-products.xml");
    public static final Path XML_USERS_AND_PRODUCTS_PATH =
            Path.of("src", "main", "resources", "output", "xml", "users-and-products.xml");

}
