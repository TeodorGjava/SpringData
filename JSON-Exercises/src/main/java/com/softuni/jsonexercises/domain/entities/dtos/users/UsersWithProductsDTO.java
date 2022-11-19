package com.softuni.jsonexercises.domain.entities.dtos.users;

import com.softuni.jsonexercises.domain.entities.dtos.products.ProductsSoldWithCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithProductsDTO {
    private String firstName;
    private String lastName;
    private Integer age;

    private ProductsSoldWithCountDTO products;

}
