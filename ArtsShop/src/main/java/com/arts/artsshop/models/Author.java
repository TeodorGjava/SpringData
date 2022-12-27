package com.arts.artsshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Lob
    @Column(name = "image", length = 1000)
    private byte[] image;
    @Column
    private String fullName;
    @Column
    private String bio;
    @OneToMany
    private List<Product> products;


}
