package com.softuni.springbootintroexercises;


import com.softuni.springbootintroexercises.domain.services.author.AuthorService;
import com.softuni.springbootintroexercises.domain.services.book.BookService;
import com.softuni.springbootintroexercises.domain.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


@Component
public class ConsoleRunner implements CommandLineRunner {
    private final LocalDate BOOK_AFTER_YEAR = LocalDate.of(2000, 1, 1);
    private final LocalDate AUTHOR_BY_BOOKS_BEFORE = LocalDate.of(1990, 1, 1);
    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    public ConsoleRunner(SeedService seedService, BookService bookService, AuthorService authorService) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    private void getBookAfterYear() {
        this.bookService.findAllByReleaseDateAfter
                (BOOK_AFTER_YEAR).forEach(b -> System.out.println(b.getTitle()));

    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAllData();
        //this.getBookAfterYear()
        //this.getAllAuthorsByBooksBefore();
        this.findAllByFirstNameLastName("George", "Powell");
    }
    //this.getAllAuthorsOrderByBooks();

    private void getAllAuthorsByBooksBefore() {
        this.authorService.findDistinctByBooksBefore(AUTHOR_BY_BOOKS_BEFORE)
                .forEach(author -> System.out.printf("%s %s%n", author.getFirstName(), author.getLastName()));
    }

    private void getAllAuthorsOrderByBooks() {
      // this.authorService.findAllOrderByBooks()
      //         .forEach(author -> System.out.printf("%s %s %d%n", author.getFirstName()
      //                 , author.getLastName(), author.getBooks().size()));
    }

    public void findAllByFirstNameLastName(String firstName, String lastName) {
        this.bookService
                .findAllByFirstNameLastName(
                        firstName, lastName
                )
                .forEach(book -> System.out.printf("%s %s %d%n",
                        book.getTitle(), book.getReleaseDate(), book.getCopies()));
    }
}
