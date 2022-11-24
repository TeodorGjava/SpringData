package com.softuni.xmlprocessing.domain.entities.dtos.users;

import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductBasicInfoDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductsSoldWithCountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
