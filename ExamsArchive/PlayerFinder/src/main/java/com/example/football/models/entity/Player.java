package com.example.football.models.entity;

import com.example.football.models.enums.Positions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @Size(min = 2)
    @Column(nullable = false)
    private String firstName;
    @Size(min = 2)
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Positions position;
    @ManyToOne
    private Town town;
    @ManyToOne
    private Team team;
    @OneToOne
    private Stat stats;
}
