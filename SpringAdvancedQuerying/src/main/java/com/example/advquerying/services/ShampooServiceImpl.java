package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {
    ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return new ArrayList<>(shampooRepository.findByBrand(brand));
    }

    @Override
    public List<Shampoo> findShampooBySizeOrderByIdDesc(String size) {
        return this.shampooRepository.findShampooBySizeOrderById(Size.valueOf(size.toUpperCase()));
    }

    @Override
    public List<Shampoo> findShampooByBrandAndSize(String brand, String size) {
        return shampooRepository.findShampooByBrandAndSize(brand, Size.valueOf(size.toUpperCase()));

    }

    @Override
    public List<Shampoo> findShampooBySize(String size) {
        return shampooRepository.findShampooBySize(Size.valueOf(size.toUpperCase()));
    }

    @Override
    public List<Shampoo> findByIngredient(String name) {
        return shampooRepository.findByIngredient(name);
    }


}
