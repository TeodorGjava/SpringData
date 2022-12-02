package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.ApartmentType;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;
    @Column(nullable = false)
    @DecimalMin(value = "40")
    private Double area;
    @ManyToOne
    private Town town;
    @OneToMany
    private List<Offer> offers;
}
