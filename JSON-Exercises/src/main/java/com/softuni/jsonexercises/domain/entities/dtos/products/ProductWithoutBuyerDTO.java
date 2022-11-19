package com.softuni.jsonexercises.domain.entities.dtos.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductWithoutBuyerDTO {
    private String name;

    private BigDecimal price;

    private String seller;
}
