package com.softuni.cardealer.domain.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    @Column
    private String name;
    @Column(name = "is_importer",nullable = false)
    private Boolean isImporter;
    @OneToMany(targetEntity = Part.class,mappedBy = "supplier")
    private Set<Part> parts;

    public Supplier() {
        this.parts = new HashSet<>();
    }

    public Supplier(String name, Boolean isImporter) {
        this();
        this.name = name;
        this.isImporter = isImporter;
    }
}
