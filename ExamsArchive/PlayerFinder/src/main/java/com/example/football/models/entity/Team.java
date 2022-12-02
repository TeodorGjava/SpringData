package com.example.football.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    @Size(min = 3)
    @Column(nullable = false, unique = true)
    private String name;
    @Size(min = 3)
    @Column(nullable = false)
    private String stadiumName;
    @Min(value = 1000)
    @Column(nullable = false)
    private Integer fanBase;
    @Size(min = 10)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String history;
    @ManyToOne
    private Town town;
}
