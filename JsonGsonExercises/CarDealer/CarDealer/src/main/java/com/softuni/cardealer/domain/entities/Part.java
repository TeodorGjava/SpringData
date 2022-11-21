package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;
    @ManyToOne
    private Supplier supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(name, part.name)
                && Objects.equals(price, part.price)
                && Objects.equals(getId(), part.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, getId());
    }


}
