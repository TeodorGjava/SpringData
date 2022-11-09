package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

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
        return shampooRepository.findAll()
                .stream()
                .filter(x -> x.getBrand().equals(brand))
                .collect(Collectors.toList());
    }

    @Override
    public List<Shampoo> findShampooBySizeOrderById(Size size) {
        return this.shampooRepository.findShampooBySizeOrderById(size);
    }
}
