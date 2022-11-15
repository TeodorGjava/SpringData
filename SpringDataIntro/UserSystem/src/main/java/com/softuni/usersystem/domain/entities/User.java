package com.softuni.usersystem.domain.entities;

import com.softuni.usersystem.domain.annotations.Password;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

import static com.softuni.usersystem.domain.entities.User.messages.TOO_OLD_MESSAGE;
import static com.softuni.usersystem.domain.entities.User.messages.TOO_YOUNG_MESSAGE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usesrs")
public class User extends BaseEntity {
    enum messages {
        ;
        public static final String TOO_YOUNG_MESSAGE = "Age cannot be less than 1";
        public static final String TOO_OLD_MESSAGE = "Age cannot be more than 120";
    }

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 30)
    private String userName;

    @Column
    @Password
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private Date registeredOn;
    @Column
    private Date lastTimeLoggedIn;
    @Column
    @Min(value = 1, message = TOO_YOUNG_MESSAGE)
    @Max(value = 120, message = TOO_OLD_MESSAGE)
    private Integer age;
    @Column
    private Boolean isDeleted;

    @OneToMany
    private Set<Picture> profilePictures;

    public String getFullNameAndAge() {
        return String.format("%s %s - %d years old", this.firstName, this.lastName, this.age);
    }

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    private Set<User> friends;
    @ManyToOne
    private Town livingIn;

    @ManyToOne
    private Town bornIn;
    @Column
    private String firstName;
    @Column
    private String lastName;

}
