package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllByNameStartsWith(String str);
    void deleteByIngredientName(String name);
    void increasePrice();
    void increasePriceWhereNameIn(String string);
}
