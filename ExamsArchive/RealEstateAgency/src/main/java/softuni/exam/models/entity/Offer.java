package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{
    @Column(nullable = false)
    @Min(1)
    private Double price;
    @Column(nullable = false)
    private LocalDate publishedOn;
    @ManyToOne
    private Apartment apartment;
    @ManyToOne
    private Agent agent;


}
