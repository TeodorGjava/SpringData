package com.softuni.xmlprocessing.domain.entities.dtos.products;


import com.softuni.xmlprocessing.domain.entities.dtos.categories.CategoryDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;

    private BigDecimal price;

    private UserDTO buyer;
    private UserDTO seller;

    private Set<CategoryDTO> categories;

    public ProductWithoutBuyerDTO productInRangeWithoutBuyerDTO() {
        return new ProductWithoutBuyerDTO(name, price, seller.getFullName());
    }


}
