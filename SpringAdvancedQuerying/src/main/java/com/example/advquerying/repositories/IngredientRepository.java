package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findAllByNameStartsWith(String str);

    @Query(value = "DELETE from Ingredient i where i.name = :name")
    @Modifying
    void deleteByIngredientName(String name);

    @Query(value = "UPDATE Ingredient i set i.price = i.price*1.1")
    @Modifying
    void increasePrice();
    @Query(value = "UPDATE Ingredient i set i.price = i.price*1.1 where i.name = :string")
    @Modifying
    void increasePriceWhereNameIn(String string);
}
