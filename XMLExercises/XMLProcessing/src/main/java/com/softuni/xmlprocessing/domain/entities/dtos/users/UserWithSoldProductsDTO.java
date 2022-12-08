package com.softuni.xmlprocessing.domain.entities.dtos.users;

import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductWithBuyerFirstAndLastNameDto;
import com.softuni.xmlprocessing.domain.entities.dtos.products.ProductWithBuyerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserWithSoldProductsDTO {
    private String firstName;

    private String lastName;

    private List<ProductWithBuyerInfoDTO> boughtProducts;

    public static List<UserSoldProductsDTOXML> toUsersWithSoldProductsDto(List<UserWithSoldProductsDTO> input) {
        return input.stream()
                .map(user -> new UserSoldProductsDTOXML(
                        user.getFirstName(),
                        user.getLastName(),
                        new XMLSoldProductsWrapperDto(user.getBoughtProducts())))
                .toList();
    }
}
