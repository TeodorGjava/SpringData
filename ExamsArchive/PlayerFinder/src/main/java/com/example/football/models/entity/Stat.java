package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "your_custom_sequence")
    @SequenceGenerator(name="your_custom_sequence", sequenceName = "MY_CUSTOM_SEQ", allocationSize=1)
    private Long id;
    @DecimalMin(value = "1")
    @Column(nullable = false)
    private Double shooting;
    @DecimalMin(value = "1")
    @Column(nullable = false)
    private Double passing;
    @DecimalMin(value = "1")
    @Column(nullable = false)
    private Double endurance;
}
