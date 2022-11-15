package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String restriction);

    List<Book> findAllByEditionTypeAndCopies(String editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(String low, String high);

    List<Book> findAllByReleaseDateNotLike(String date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String str);

    List<Book> findAllByAuthorLastNameStartingWith(String str);

    Integer findAllBooksWithTitleLengthMoreThan(Integer length);

    Integer numberOfCopiesByAuthor(String name);

    List<String> findByTitle(String title);

    Optional<Integer> increaseBookCopiesAfterGivenDate(LocalDate date, Integer copies);
    Integer deleteBooksByCopiesLessThan(Integer copies);
}
