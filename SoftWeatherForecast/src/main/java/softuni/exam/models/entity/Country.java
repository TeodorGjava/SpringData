package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country extends BaseEntity {
    @Size(min = 2, max = 60)
    @Column(nullable = false,unique = true)
    private String countryName;
    @Column(nullable = false,unique = true)
    @Size(min = 2, max = 60)
    private String currency;
    @OneToMany
    private List<City> cities;
}
