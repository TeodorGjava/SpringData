package com.softuni.xmlprocessing.domain.repositories;


import com.softuni.xmlprocessing.domain.entities.Category;
import com.softuni.xmlprocessing.domain.entities.dtos.categories.CategoryProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from `product-shop`.categories order by RAND() limit 1", nativeQuery = true)
    Optional<Category> getRandomCategory();

    @Query("select new com.softuni.xmlprocessing.domain.entities.dtos.categories.CategoryProductDTO(" +
            "c.name, count(p.id),avg(p.price),sum(p.price)) " +
            "from Product p join" +
            " p.categories c " +
            "group by c.id " +
            "order by count(p.id)")
    Optional<List<CategoryProductDTO>> getCategoriesSummary();
}
