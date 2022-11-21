package com.softuni.cardealer.domain.entities.dtos.sales;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportSaleDTO {

    private Car car;

    private Customer customer;

    private BigDecimal discount;
}
