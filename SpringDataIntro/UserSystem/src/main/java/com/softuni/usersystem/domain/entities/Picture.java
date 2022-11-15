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
@Table(name = "pictures")
public class Picture extends BaseEntity{
    @Column
    private String title;
    @Column
    private String caption;
    @Column
    private String path;
    @OneToMany
    private Set<Album> albums;
}
