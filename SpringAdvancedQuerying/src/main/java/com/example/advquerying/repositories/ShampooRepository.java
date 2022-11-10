package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Enumeration;
import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findShampooBySizeOrderById(Size size);

    List<Shampoo> findShampooByBrandAndSize(String brand, Size size);


}
