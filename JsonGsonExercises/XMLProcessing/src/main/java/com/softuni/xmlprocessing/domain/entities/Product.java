package com.softuni.xmlprocessing.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column
    private BigDecimal price;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;
    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;
//name (at least 3 characters),
// price,
// buyerId (optional)
// and sellerId as IDs of users.
}
