package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class IngredientServiceImpl implements IngredientService {

    IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameStartsWith(String str) {
        return this.ingredientRepository.findAllByNameStartsWith("M");
    }

    @Override
    @Transactional
    public void deleteByIngredientName(String name) {
        this.ingredientRepository.deleteByIngredientName(name);
    }

    @Override
    @Transactional
    public void increasePrice() {
        this.ingredientRepository.increasePrice();
    }

    @Override
    @Transactional
    public void increasePriceWhereNameIn(String strings) {
        this.ingredientRepository.increasePriceWhereNameIn(strings);
    }

}
