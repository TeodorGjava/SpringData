package com.softuni.jsonexercises.domain.entities.json.dtos.users;

import com.softuni.jsonexercises.domain.entities.json.dtos.products.ProductsSoldWithCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
