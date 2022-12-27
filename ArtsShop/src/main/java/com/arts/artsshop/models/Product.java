package com.arts.artsshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] image;
    @Column(nullable = false)
    private String sizes;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column
    @DecimalMin(value = "0.5")
    private Double price;
    @ManyToOne
    private Category category;
    @ManyToMany
    private List<Order> orders;

}
