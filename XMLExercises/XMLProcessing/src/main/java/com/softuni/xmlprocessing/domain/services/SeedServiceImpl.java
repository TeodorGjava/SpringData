package com.softuni.xmlprocessing.domain.services;

import com.softuni.xmlprocessing.domain.entities.Category;
import com.softuni.xmlprocessing.domain.entities.Product;
import com.softuni.xmlprocessing.domain.entities.User;
import com.softuni.xmlprocessing.domain.entities.dtos.categories.ImportCategoryWrapperDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ImportProductWrapperDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.users.ImportUserWrapperDTO;
import com.softuni.xmlprocessing.domain.repositories.CategoryRepository;
import com.softuni.xmlprocessing.domain.repositories.ProductRepository;
import com.softuni.xmlprocessing.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static com.softuni.xmlprocessing.constants.Paths.*;
import static com.softuni.xmlprocessing.constants.Utils.MODEL_MAPPER;


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
    public void seedUsers() throws FileNotFoundException, JAXBException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(XML_USERS_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(ImportUserWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final ImportUserWrapperDTO usersDTO = (ImportUserWrapperDTO) unmarshaller.unmarshal(fileReader);

            final List<User> users = usersDTO.getUsers()
                    .stream()
                    .map(importUserDTO -> MODEL_MAPPER.map(importUserDTO, User.class)).toList();

            this.userRepository.saveAllAndFlush(users);
        }
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(XML_CATEGORIES_PATH.toFile());


            final JAXBContext context = JAXBContext.newInstance(ImportCategoryWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final ImportCategoryWrapperDTO unmarshal = (ImportCategoryWrapperDTO) unmarshaller.unmarshal(fileReader);
            List<Category> categories = unmarshal.getCategories()
                    .stream()
                    .map(categoryImportDTO -> MODEL_MAPPER.map(categoryImportDTO, Category.class)).toList();
            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productRepository.count() == 0) {
            final FileReader fileReader = new FileReader(XML_PRODUCTS_PATH.toFile());
            /*

            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDTO[].class))
                    .map(productImportDTO -> MODEL_MAPPER.map(productImportDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .toList();
             */
            final JAXBContext context = JAXBContext.newInstance(ImportProductWrapperDTO.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final ImportProductWrapperDTO importProductWrapperDTO = (ImportProductWrapperDTO) unmarshaller.unmarshal(fileReader);

            List<Product> products = importProductWrapperDTO.getProducts()
                    .stream()
                    .map(importProductDTO -> MODEL_MAPPER.map(importProductDTO, Product.class))
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
        long highEnd = this.categoryRepository.count();
        int numberOfCategories = random.nextInt((int) highEnd);
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
