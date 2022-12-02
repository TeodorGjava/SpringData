package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities")
public class Town extends BaseEntity {
    @Size(min = 2)
    @Column(unique = true,nullable = false)
    private String townName;
    @Column(nullable = false)
    @Min(value = 1)
    private Integer population;

    @OneToMany
    private List<Agent> agents;
    @OneToMany
    private List<Apartment> apartments;
}
