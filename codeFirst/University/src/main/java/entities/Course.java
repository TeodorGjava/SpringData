package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private Integer credits;
    @OneToMany
    @JoinColumn
    private Set<Student> students;
    @OneToOne
    @JoinColumn
    private Teacher teacher;

}
