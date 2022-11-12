package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


@Component
public class Main implements CommandLineRunner {
    private final ShampooService shampooService;
    private IngredientService ingredientService;

    @Autowired
    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {


        //getShampooBySize();
        //getShampooBySizeOrderByIdDesc();
        //getShampooByIngredient();
        //getShampooByBrandAndSize();
        //getShampoosBySizeAndLabel();
        //getIngredientStartingWith();
        //System.out.println(getCountShampoosWithPriceLessThan("8.5"));
        //selectShampoosWithIngredientsCount(2);
        //deleteByIngredientName("Cherry");
        //increasePrice();
        /*String next = new Scanner(System.in).nextLine();

        while (!next.isBlank()) {

            increasePriceWhereNameIn(next);
        }
*/
        

    }

    private void increasePriceWhereNameIn(String strings) {
        this.ingredientService.increasePriceWhereNameIn(strings);
    }

    private void increasePrice() {
        ingredientService.increasePrice();
    }

    private void getShampoosBySizeAndLabel() {
        shampooService.findShampooBySizeOrLabelOrderByPrice("MEDIUM", 10L)
                .forEach(shampoo -> System.out.println(shampoo.toString()));
    }

    private void getShampooByIngredient() {
        shampooService.findByIngredient("Berry")
                .forEach(shampoo -> System.out.println(shampoo.toString()));
    }

    private void getShampooBySizeOrderByIdDesc() {
        shampooService.findShampooBySizeOrderByIdDesc("MEDIUM")
                .forEach(shampoo -> System.out.println(shampoo.toString()));
    }

    private void getShampooBySize() {
        shampooService.findShampooBySize("medium").forEach(sh ->
                System.out.println(sh.toString()));
    }

    private void getShampooByBrandAndSize() {
        shampooService.findShampooByBrandAndSize("Volume & Fullness Lavender", "medium")
                .forEach(s -> System.out.print(s.toString()));
    }

    private void getIngredientStartingWith() {
        this.ingredientService.findAllByNameStartsWith("M")
                .forEach(ingredient -> System.out.println(ingredient.toString()));
    }

    private int getCountShampoosWithPriceLessThan(String price) {
        return shampooService.findAllByPriceLessThan(price).stream().toList().size();
    }

    private void selectShampoosWithIngredientsCount(int count) {
        for (Shampoo shampoo : shampooService.selectShampoosWithIngredientsCount(count)) {
            System.out.println(shampoo.toString());
        }
    }

    public void deleteByIngredientName(String name) {
        this.ingredientService.deleteByIngredientName(name);
    }
}
