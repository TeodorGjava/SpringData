package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 19)
    private String partName;

    @Column(nullable = false)
    @DecimalMin("10")
    @DecimalMax("2000")
    private Double price;

    @Column(nullable = false)
    @Min(1)
    private Integer quantity;

    @OneToMany
    private List<Task> tasks;
}
