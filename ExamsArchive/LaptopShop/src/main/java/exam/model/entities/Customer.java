package exam.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Size(min = 2)
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    @Size(min = 2)
    private String lastName;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private LocalDate registeredOn;
    @ManyToOne
    private Town town;

}
