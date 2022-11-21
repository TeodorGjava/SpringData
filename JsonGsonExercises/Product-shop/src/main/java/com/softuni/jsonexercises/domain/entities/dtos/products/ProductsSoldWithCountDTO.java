package com.softuni.jsonexercises.domain.entities.dtos.products;

import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductBasicInfoDTO;
import com.softuni.jsonexercises.domain.entities.dtos.users.UsersWithProductsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ProductsSoldWithCountDTO {
    private Integer usersCount;
    private List<ProductBasicInfoDTO> products;

    public ProductsSoldWithCountDTO(List<ProductBasicInfoDTO> products) {
        this.products = products;
        this.usersCount = products.size();
    }
}
