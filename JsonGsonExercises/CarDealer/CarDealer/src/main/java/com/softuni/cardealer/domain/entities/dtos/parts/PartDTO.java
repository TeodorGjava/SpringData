package com.softuni.cardealer.domain.entities.dtos.parts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class PartDTO {

    private String name;

    private BigDecimal price;
}
