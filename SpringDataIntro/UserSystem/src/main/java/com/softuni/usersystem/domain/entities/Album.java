package com.softuni.usersystem.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String backgroundColor;
    @OneToMany
    private Set<Picture> pictures;
}
