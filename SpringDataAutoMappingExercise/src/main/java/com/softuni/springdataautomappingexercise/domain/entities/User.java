package com.softuni.springdataautomappingexercise.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.softuni.springdataautomappingexercise.domain.constants.Validations.EMAIL_PATTERN;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false)
    @Email(regexp = EMAIL_PATTERN)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE})
    private Set<Game> games;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Order> orders;
    @Column(nullable = false)
    private Boolean isAdministrator;
    @Column(nullable = false)
    private Boolean isOnline;


    public void setOnline() {
        this.isOnline = true;
    }

    public void setOffline() {
        this.isOnline = false;
    }

    public Boolean getOnline() {
        return this.isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email)
                && password.equals(user.password)
                && fullName.equals(user.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, fullName);
    }

    public User() {
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public User(String email, String password, String fullName) {
        this();
        this.email = email;
        this.password = password;
        this.fullName = fullName;

    }

    public String addGame(Game game) {
        this.games.add(game);
        return game.getTitle() + " added to user";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Boolean getAdministrator() {
        return isAdministrator;
    }

    public String successfulLogin() {
        return String.format("Successfully logged in %s", this.fullName);
    }

    public void setAdministrator(Boolean administrator) {
        isAdministrator = administrator;
    }
}
