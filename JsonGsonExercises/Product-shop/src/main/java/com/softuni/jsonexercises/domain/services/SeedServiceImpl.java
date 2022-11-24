package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.Category;
import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.User;
import com.softuni.jsonexercises.domain.entities.json.dtos.categories.CategoryImportDTO;
import com.softuni.jsonexercises.domain.entities.json.dtos.products.ProductImportDTO;
import com.softuni.jsonexercises.domain.entities.json.dtos.users.ImportUserDTO;
import com.softuni.jsonexercises.domain.entities.xml.dtos.ImportUserXMLDTO;
import com.softuni.jsonexercises.domain.entities.xml.dtos.UsersImportWrapperDTO;
import com.softuni.jsonexercises.domain.repositories.CategoryRepository;
import com.softuni.jsonexercises.domain.repositories.ProductRepository;
import com.softuni.jsonexercises.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static com.softuni.jsonexercises.constants.JsonPaths.*;
import static com.softuni.jsonexercises.constants.Utils.GSON;
import static com.softuni.jsonexercises.constants.Utils.MODEL_MAPPER;
import static com.softuni.jsonexercises.constants.XmlPaths.XML_USERS_PATH;

@Service
public class SeedServiceImpl implements SeedService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {

        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(XML_USERS_PATH.toFile());
           /*
           *List<User> users = Arrays.stream(GSON.fromJson(fileReader, ImportUserDTO[].class))
                    .map(userDTo -> MODEL_MAPPER.map(userDTo, User.class)).toList();
            this.userRepository.saveAllAndFlush(users); */
            final JAXBContext context = JAXBContext.newInstance(UsersImportWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            ImportUserXMLDTO importUsersDTO = (ImportUserXMLDTO) unmarshaller.unmarshal(fileReader);
            importUsersDTO.get
            fileReader.close();
        }

        JAXBContext.newInstance(ImportUserDTO.class);

    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_CATEGORIES_PATH.toFile());
            final List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDTO[].class))
                    .map(categoryImportDTO -> MODEL_MAPPER.map(categoryImportDTO, Category.class)).toList();
            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_PRODUCTS_PATH.toFile());
            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDTO[].class))
                    .map(productImportDTO -> MODEL_MAPPER.map(productImportDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .toList();
            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomCategory(Product product) {
        Category category = this.categoryRepository.getRandomCategory().orElseThrow(NoSuchElementException::new);
        Random random = new Random();
        Long highEnd = this.categoryRepository.count();
        int numberOfCategories = random.nextInt(highEnd.intValue());
        Set<Category> categories = new HashSet<>();

        IntStream.rangeClosed(1, numberOfCategories)
                .forEach(num -> {
                    this.categoryRepository.getRandomCategory()
                            .orElseThrow(NoSuchElementException::new);
                    categories.add(category);
                });

        product.setCategories(categories);
        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(new BigDecimal(800L)) > 0) {
            User buyer = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
            while (!buyer.equals(product.getSeller())) {
                buyer = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
            }
            product.setBuyer(buyer);

        }
        return product;
    }

    private Product setRandomSeller(Product product) {
        final User user = this.userRepository.getRandomUser().orElseThrow(NoSuchElementException::new);
        product.setSeller(user);
        return product;
    }

}
