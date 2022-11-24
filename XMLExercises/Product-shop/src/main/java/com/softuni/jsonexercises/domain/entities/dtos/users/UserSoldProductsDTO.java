package com.softuni.jsonexercises.domain.entities.dtos.users;

import com.softuni.jsonexercises.domain.entities.dtos.products.ProductDTO;
import com.softuni.jsonexercises.domain.entities.dtos.products.SoldProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSoldProductsDTO {
    private String firstName;
    private String lastName;
    private List<SoldProductDTO> boughtProducts;

}
