package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31)).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1)).orElseThrow(NoSuchElementException::new)
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    private LocalDate getDateFromConsole(String dateFromConsole) {
        final String format = "dd-MM-yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);

        return LocalDate.parse(dateFromConsole, formatter);
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByAgeRestriction(String restriction) {
        return bookRepository.findAllByAgeRestriction(AgeRestriction.valueOf(restriction.toUpperCase())).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopies(String editionType, Integer copies) {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(
                EditionType.valueOf(editionType.toUpperCase()), copies).orElseThrow(NoSuchElementException::new);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }


    @Override
    public List<Book> findAllByReleaseDateNotLike(String date) {
        return bookRepository
                .findAllByReleaseDateBefore(getDateFromConsole(date))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(LocalDate date) {
        return this.bookRepository
                .findAllByReleaseDateBefore(date)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<Integer> increaseBookCopiesAfterGivenDate(LocalDate date, Integer copies) {
        Optional<Integer> books = this.bookRepository.increaseBookCopiesAfterGivenDate(date, copies);

        return books;
    }

    @Override
    public Integer deleteBooksByCopiesLessThan(Integer copies) {
        return this.bookRepository.deleteBooksByCopiesLessThan(copies).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<String> findByTitle(String title) {
        return new ArrayList<>(this.bookRepository
                .findByTitle(title).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public Integer findAllBooksWithTitleLengthMoreThan(Integer length) {
        return this.bookRepository
                .findAllBooksWithTitleLengthMoreThan(length)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Integer numberOfCopiesByAuthor(String name) {
        return this.bookRepository.numberOfCopiesByAuthor(name)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartingWith(String str) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(str).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByTitleContaining(String str) {
        return this.bookRepository.findAllByTitleContaining(str.toLowerCase()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(String low, String high) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(new BigDecimal(low), new BigDecimal(high)).orElseThrow(NoSuchElementException::new);
    }
}
