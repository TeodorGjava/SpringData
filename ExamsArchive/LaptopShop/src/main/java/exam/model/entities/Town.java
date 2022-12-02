package exam.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column(nullable = false,unique = true)
    @Size(min = 2)
    private String name;
    @Column(nullable = false)
    @Min(1)
    private Integer population;
    @Column(nullable = false,columnDefinition = "TEXT")
    @Size(min = 10)
    private String travelGuide;
}

