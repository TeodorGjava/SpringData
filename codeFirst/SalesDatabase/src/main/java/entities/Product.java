package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column
    private String name;

    @Column
    private Double quantity;

    @Column
    private BigDecimal price;

    @OneToMany
    @JoinColumn
    private Set<Sale> sales;
}
