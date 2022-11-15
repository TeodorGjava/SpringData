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
import java.util.List;
import java.util.Optional;
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

        //10.Total Book Copies
        //getTotalBookCopiesByAuthor();

        //11.Reduced Book
        //getReducedBook();

        //12Increase Book Copies
        // increaseBookCopies();

        //13.Remove Books
        //deleteBooksWithCopiesLessThan();

        //14.StoredProcedure
    }

    private void deleteBooksWithCopiesLessThan() {
        int copiesToDelete = new Scanner(System.in).nextInt();
        Integer deletedCopies = this.bookService.deleteBooksByCopiesLessThan(copiesToDelete);
        System.out.println(deletedCopies);
    }

    private void increaseBookCopies() {
        Scanner scanner = new Scanner(System.in);


        String[] date = scanner.nextLine().split(" ");
        int day = Integer.parseInt(date[0]);
        int month = switch (date[1]) {
            case "Jan" -> 1;
            case "Feb" -> 2;
            case "Mar" -> 3;
            case "Apr" -> 4;
            case "May" -> 5;
            case "Jun" -> 6;
            case "Jul" -> 7;
            case "Aug" -> 8;
            case "Sep" -> 9;
            case "Oct" -> 10;
            case "Nov" -> 11;
            case "Dec" -> 12;
            default -> {
                throw new IllegalArgumentException("No such month!");
            }
        };
        int year = Integer.parseInt(date[2]);
        String copies = scanner.nextLine();
        int copiesParsed = Integer.parseInt(copies);

        Optional<Integer> x = bookService.increaseBookCopiesAfterGivenDate(LocalDate.of(year, month, day), copiesParsed);
        if (x.isPresent()) {
            int i = x.get();
            System.out.println(i * copiesParsed);
        }
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
