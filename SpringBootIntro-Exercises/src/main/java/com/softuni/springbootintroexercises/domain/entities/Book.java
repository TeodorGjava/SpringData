package com.softuni.springbootintroexercises.domain.entities;

import com.softuni.springbootintroexercises.domain.enums.AgeRestriction;
import com.softuni.springbootintroexercises.domain.enums.EditionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer copies;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Column(nullable = false)
    private Double price;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    private Author author;

    @ManyToMany
    private Set<Category> categories;


}
