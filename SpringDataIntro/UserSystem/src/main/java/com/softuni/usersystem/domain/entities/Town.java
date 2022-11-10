package com.softuni.usersystem.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column
    private String name;
    @Column
    private String country;

    @OneToMany(mappedBy = "bornIn")
    private Set<User> bornIn;
    @OneToMany(mappedBy = "livingIn")
    private Set<User> livingIn;
}
