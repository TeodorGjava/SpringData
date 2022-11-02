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
@Table(name = "continents")
public class Continent extends BaseEntity{

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "continents")
    private Set<Country> countries;
}
