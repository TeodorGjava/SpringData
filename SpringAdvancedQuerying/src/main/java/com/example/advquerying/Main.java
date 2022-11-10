package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Main implements CommandLineRunner {
    private final ShampooService shampooService;

    @Autowired
    public Main(ShampooService shampooService) {
        this.shampooService = shampooService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (Shampoo shampoo : shampooService.findByBrand("Silk Comb")) {
            System.out.println(shampoo.getId());

            for (Shampoo shampoo1 : shampooService.findShampooByBrandAndSize("Volume & Fullness Lavender", "medium")) {
                shampoo1.toString();
            }
        }

    }
}
