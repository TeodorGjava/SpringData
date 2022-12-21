package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mechanics")
public class Mechanic extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Size(min = 2)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2)
    private String lastName;

    @Email
    @Size(min = 2)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    @Size(min = 2)
    private String phone;

    @OneToMany
    private List<Task> tasks;


}
