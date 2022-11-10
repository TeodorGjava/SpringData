package com.softuni.springbootintroexercises.domain.services.author;

import com.softuni.springbootintroexercises.domain.entities.Author;
import com.softuni.springbootintroexercises.domain.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public Author getRandomAuthor() {
        long count = this.authorRepository.count();
        if (count != 0) {
            long randomId = new Random().nextLong(1L, count) + 1;
            return this.authorRepository.findAuthorById(randomId).orElseThrow(NoSuchElementException::new);
        }
        throw new RuntimeException("Error");
    }

    @Override
    public List<Author> findDistinctByBooksBefore(LocalDate date) {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(date).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Author> findAllOrderByBooks() {
        return this.authorRepository.
                findAllDistinctOrderByBooks().orElseThrow(NoSuchElementException::new);
    }
}
