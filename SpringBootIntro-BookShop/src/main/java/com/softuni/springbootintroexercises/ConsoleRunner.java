package com.softuni.springbootintroexercises;


import com.softuni.springbootintroexercises.domain.entities.Author;
import com.softuni.springbootintroexercises.domain.services.author.AuthorService;
import com.softuni.springbootintroexercises.domain.services.book.BookService;
import com.softuni.springbootintroexercises.domain.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


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


    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAllData();
        //this.getBookAfterYear()
        //this.getAllAuthorsByBooksBefore();
        //this.findAllByFirstNameLastName("George", "Powell");
        this.getAllAuthorsOrderByBooks();
    }
    //this.getAllAuthorsOrderByBooks();

    private void getAllAuthorsByBooksBefore() {
        this.authorService.findDistinctByBooksBefore(AUTHOR_BY_BOOKS_BEFORE)
                .forEach(author -> System.out.println(author.toStringAndSize()));
    }

    private void getBookAfterYear() {
        this.bookService.findAllByReleaseDateAfter
                (BOOK_AFTER_YEAR).forEach(b -> System.out.println(b.getTitle()));

    }

    private void getAllAuthorsOrderByBooks() {
        this.authorService.findAllOrderByBooks().forEach(a -> System.out.println(a.toStringAndSize()));

    }

    public void findAllByFirstNameLastName(String firstName, String lastName) {
        this.bookService
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(
                        firstName, lastName
                )
                .forEach(book -> System.out.printf("%s %s %d%n",
                        book.getTitle(), book.getReleaseDate(), book.getCopies()));
    }
}
