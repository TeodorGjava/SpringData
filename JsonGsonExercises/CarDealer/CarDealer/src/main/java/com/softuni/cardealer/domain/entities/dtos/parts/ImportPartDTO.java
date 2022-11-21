package com.softuni.cardealer.domain.entities.dtos.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportPartDTO {
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
