package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {
    @Column
    private String locationName;
    @OneToMany
    @JoinColumn
    private Set<Sale> sales;
}
