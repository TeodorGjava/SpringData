package com.softuni.springbootintroexercises.domain.services.seed;

import com.softuni.springbootintroexercises.domain.entities.Author;
import com.softuni.springbootintroexercises.domain.entities.Book;
import com.softuni.springbootintroexercises.domain.entities.Category;
import com.softuni.springbootintroexercises.domain.enums.AgeRestriction;
import com.softuni.springbootintroexercises.domain.enums.EditionType;
import com.softuni.springbootintroexercises.domain.services.author.AuthorService;
import com.softuni.springbootintroexercises.domain.services.book.BookService;
import com.softuni.springbootintroexercises.domain.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.springbootintroexercises.domain.constants.FilePath.*;

@Service
public class SeedServiceImpl implements SeedService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public SeedServiceImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            this.authorService.seedAuthors(Files.readAllLines(
                            Path.of(URL_FOLDER + AUTHORS))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(s -> Author.builder()
                            .firstName(s.split(" ")[0])
                            .lastName(s.split(" ")[1])
                            .build()).collect(Collectors.toList()));

        }

        System.out.println();
    }

    @Override
    public void seedBooks() throws IOException {
       final List<Book> books = Files.readAllLines(Path.of(URL_FOLDER, BOOK_FILE_NAME))
                .stream()
                .map(row -> {
                    String[] data = row.split("\\s+");
                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                    String title = Arrays.stream(data)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    return Book.builder().title(title)
                            .editionType(editionType)
                            .price(Double.parseDouble(data[3]))
                            .author(this.authorService.getRandomAuthor())
                            .ageRestriction(AgeRestriction
                                    .values()[Integer.parseInt(data[4])])
                            .copies(Integer.parseInt(data[2]))
                            .categories(this.categoryService.getRandomCategories())
                            .releaseDate(LocalDate.parse(data[1],
                                    DateTimeFormatter.ofPattern("d/M/yyyy")))
                            .build();
                }).collect(Collectors.toList());
        this.bookService.seedBooks(books);
    }

    @Override
    public void seedCategory() throws IOException {
        if (!this.categoryService.isDataSeeded()) {
            this.categoryService.seedCategories(Files.readAllLines(
                            Path.of(URL_FOLDER + CATEGORY_FILE_NAME)).stream()
                    .filter(s -> !s.isBlank())
                    .map(name -> Category.builder().name(name).build())
                    .collect(Collectors.toList()));
        }
    }

}
