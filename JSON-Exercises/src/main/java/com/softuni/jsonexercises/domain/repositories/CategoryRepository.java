package com.softuni.jsonexercises.domain.repositories;

import com.softuni.jsonexercises.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from `json`.categories order by RAND() limit 1", nativeQuery = true)
    Optional<Category> getRandomCategory();
}
