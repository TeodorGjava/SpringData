package com.softuni.jsonexercises.domain.entities.dtos.users;

import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;

    private int age;

    private Set<Product> sellingProducts;

    private Set<Product> boughtProducts;

    private Set<User> friends;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
