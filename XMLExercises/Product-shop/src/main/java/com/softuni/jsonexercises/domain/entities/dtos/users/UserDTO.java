package com.softuni.jsonexercises.domain.entities.dtos.users;

import com.softuni.jsonexercises.domain.entities.Product;
import com.softuni.jsonexercises.domain.entities.User;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductBasicInfoDTO;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductDTO;
import com.softuni.jsonexercises.domain.entities.dtos.products.ProductsSoldWithCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;

    private int age;

    private Set<ProductDTO> sellingProducts;

    private Set<ProductDTO> boughtProducts;

    private Set<UserDTO> friends;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public UsersWithProductsWrapperDTO toUsersWithProductsWrapperDTO() {
        return new UsersWithProductsWrapperDTO();
    }

    public UsersWithProductsDTO toUsersWithProductsDTO() {
        return new UsersWithProductsDTO(firstName, lastName, age, toProductsSoldWithCountDTO());
    }

    public ProductsSoldWithCountDTO toProductsSoldWithCountDTO() {
        return new ProductsSoldWithCountDTO(sellingProducts
                .stream()
                .map(this::toProductBasicInfoDTO).collect(Collectors.toList()));
    }

    public ProductBasicInfoDTO toProductBasicInfoDTO(ProductDTO productDTO) {
        return new ProductBasicInfoDTO(productDTO.getName(), productDTO.getPrice());
    }
}
