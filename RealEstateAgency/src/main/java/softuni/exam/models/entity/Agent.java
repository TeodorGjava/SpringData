package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity {
    @Column(unique = true, nullable = false)
    @Size(min = 2)
    private String firstName;
    @Column(nullable = false)
    @Size(min = 2)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToOne
    private Town town;
    @OneToMany
    private List<Offer> offers;
}
