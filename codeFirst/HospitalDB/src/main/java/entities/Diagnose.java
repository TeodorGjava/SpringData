package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(name = "comments",nullable = false)
    private String comment;


}
