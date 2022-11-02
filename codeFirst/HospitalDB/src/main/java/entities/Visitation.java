package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    @Column(nullable = false)
    private LocalDate date;
    @Column(name = "comments",nullable = false)
    private String comment;

}

