package com.arts.artsshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    public Order() {
        this.products = new ArrayList<>();
    }

    @OneToOne
    private Customer customer;
    @OneToMany
    private List<Product> products;
    private double price;

    private void setPrice() {
        this.price = getPrice();
    }

    public Double getPrice() {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }
}
