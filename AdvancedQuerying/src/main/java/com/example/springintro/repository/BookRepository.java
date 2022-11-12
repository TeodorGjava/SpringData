package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    Optional<List<Book>> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    Optional<List<Book>> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction restriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    Optional<List<Book>> findAllByTitleContaining(String str);

    Optional<List<Book>> findAllByAuthorLastNameStartingWith(String str);

    @Query("select count(b) from Book b where length(b.title)>:length ")
    Optional<Integer> findAllBooksWithTitleLengthMoreThan(Integer length);

    @Query("select sum(b.copies) from Book b where concat(b.author.firstName,' ',b.author.lastName) = :name ")
    Optional<Integer> numberOfCopiesByAuthor(String name);

    @Query("select b.title,b.editionType,b.ageRestriction,b.price from Book b where b.title like :title")
    Optional<List<String>> findByTitle(String title);

    @Transactional
    @Query(value = "update Book b set b.copies= b.copies + :copies where b.releaseDate >:date")
    @Modifying
    Optional<Integer> increaseBookCopiesAfterGivenDate(LocalDate date, Integer copies);
}
