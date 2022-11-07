package com.softuni.springbootintroexercises.domain.repositories;

import com.softuni.springbootintroexercises.domain.entities.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorById(Long id);

    Optional<List<Author>> findDistinctByBooksReleaseDateBefore(LocalDate date);
  //  Optional<List<Author>> getAll();


}
