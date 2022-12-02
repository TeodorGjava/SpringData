package exam.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{
    @Size(min = 4)
    @Column(nullable = false,unique = true)
    private String name;
    @Min(20000)
    @Column(nullable = false)
    private Long income;
    @Column(nullable = false)
    @Size(min = 4)
    private String address;
    @Min(1)
    @Max(50)
    @Column(nullable = false)
    private Integer employeeCount;
    @Min(150)

    @Column
    private Integer shopArea;
    @ManyToOne(optional = false)
    private Town town;
}
