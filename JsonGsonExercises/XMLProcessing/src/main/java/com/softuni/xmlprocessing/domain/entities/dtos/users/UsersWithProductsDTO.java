package com.softuni.xmlprocessing.domain.entities.dtos.users;

import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductsSoldWithCountDTO;
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
