package com.example.advquerying.services;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findShampooBySizeOrderByIdDesc(String size);

    List<Shampoo> findShampooByBrandAndSize(String brand, String size);

    List<Shampoo> findShampooBySize(String size);

    @Query(value = "select s from Shampoo as s join s.ingredients i where i.name = :name")
    List<Shampoo> findByIngredient(String name);

   // List<Shampoo> findShampooBySizeOrLabelOrderByPrice(String size, Label label);
   List<Shampoo> selectShampoosWithIngredientsCount(int count);

    List<Shampoo> findAllByPriceLessThan(String price);

    List<Shampoo> findShampooBySizeOrLabelOrderByPrice(String size, Long id);


}
