package com.softuni.jsonexercises.domain.entities.dtos.products;

import com.softuni.jsonexercises.domain.entities.Category;
import com.softuni.jsonexercises.domain.entities.User;
import com.softuni.jsonexercises.domain.entities.dtos.categories.CategoryDTO;
import com.softuni.jsonexercises.domain.entities.dtos.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
