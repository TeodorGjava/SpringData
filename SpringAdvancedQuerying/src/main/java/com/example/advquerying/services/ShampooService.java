package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findShampooBySizeOrderByIdDesc(Size size);

    List<Shampoo> findShampooByBrandAndSize(String brand, String size);
}
