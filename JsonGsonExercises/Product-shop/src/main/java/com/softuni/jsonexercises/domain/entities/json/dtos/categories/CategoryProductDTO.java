package com.softuni.jsonexercises.domain.entities.json.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductDTO {
    private String category;
    private Long count;
    private Double averagePrice;
    private BigDecimal totalRevenue;

}
