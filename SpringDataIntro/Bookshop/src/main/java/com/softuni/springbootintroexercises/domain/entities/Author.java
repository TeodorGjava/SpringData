package com.softuni.springbootintroexercises.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table
public class Author extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(targetEntity = Book.class, mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> books;

    public String toStringAndSize() {
        return String.format("%s %s %d%n", firstName, lastName, books.size());
    }
}
