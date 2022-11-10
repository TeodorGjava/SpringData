package com.softuni.springbootintroexercises.domain.repositories;

import com.softuni.springbootintroexercises.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
