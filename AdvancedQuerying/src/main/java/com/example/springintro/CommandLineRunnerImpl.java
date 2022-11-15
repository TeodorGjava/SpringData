package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1.Books Titles by Age Restriction
        // findAllByAgeRestriction("MiNor");
        //2.Golden Book
        // findGoldenBooks("Gold", 5000);
        //3.Books by Price
        // findBooksWithPriceNotBetween("5", "40");
        //4. Not Released Books
        // findBooksNotReleasedIn(LocalDate.of(2000, 1, 1));
        // 5.Books Released Before Date
        //findBooksReleasedBefore(LocalDate.of(1992, 04, 12));
        //6.Author fname Ending with
        //findAuthorsByFirstNameEndingWith("a");
        //7.Books with titles containing
        //findBooksByTitleContaining("WOR");
        //8. Book titles Search
        //findBooksByAuthorLastNameStartsWith("Ric");
        //9.Count Books
        //System.out.println(this.bookService.findAllBooksWithTitleLengthMoreThan(2));
        //10.Total Book Copie
        //getTotalBookCopiesByAuthor();
        //11.Reduced Book
        //getReducedBook();

        //12
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();

        int increase = Integer.parseInt(scanner.nextLine());
        this.bookService.increaseBookCopiesAfterGivenDate(parseDate(date), increase);

    }

    private LocalDate parseDate(String date) {
        String formatted = date.replaceAll(" ", "-");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return LocalDate.parse(formatted, dateTimeFormatter);
    }

    private void getReducedBook() {
        List<String> reducedBookResult = getThings_fall_apart();
        System.out.println(reducedBookResult.get(0).replace(",", " "));
    }

    private List<String> getThings_fall_apart() {
        return this.bookService.findByTitle("Things Fall Apart");
    }

    private void getTotalBookCopiesByAuthor() {
        System.out.println(bookService.numberOfCopiesByAuthor("Randy Graham"));
    }

    private void findBooksByAuthorLastNameStartsWith(String str) {
        this.bookService.findAllByAuthorLastNameStartingWith(str).forEach(book -> System.out.printf("%s (%s %s)%n"
                , book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
    }


    private void findBooksByTitleContaining(String str) {
        this.bookService.findAllByTitleContaining(str).forEach(book -> System.out.println(book.getTitle()));
    }

    // 1. Books Titles by Age Restriction
    private void findAllByAgeRestriction(String ageRestriction) {
        bookService.findAllByAgeRestriction(ageRestriction).forEach(book -> System.out.println(book.getTitle()));
    }

    // 2.GoldenBooks
    private void findGoldenBooks(String editionType, Integer copies) {
        bookService.findAllByEditionTypeAndCopies(editionType, copies).forEach(book -> System.out.println(book.getTitle()));
    }

    //3. Books By Price
    private void findBooksWithPriceNotBetween(String low, String high) {
        bookService.findAllByPriceLessThanOrPriceGreaterThan(low, high)
                .forEach(book -> System.out.printf("%s - $%.2f%n", book.getTitle(), book.getPrice()));
    }

    //4. Not Released Books
    private void findBooksNotReleasedIn(String date) {
        bookService.findAllByReleaseDateNotLike(date).forEach(book -> System.out.println(book.getTitle()));
    }

    //5.
    private void findBooksReleasedBefore(LocalDate date) {
        bookService.findAllByReleaseDateBefore(date)
                .forEach(book -> System.out.printf("%s %s %.2f%n",
                        book.getTitle(), book.getEditionType(),
                        book.getPrice()));
    }

    //6.
    private void findAuthorsByFirstNameEndingWith(String str) {
        authorService.findAllByFirstNameEndingWith(str)
                .forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
