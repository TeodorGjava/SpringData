package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.products.SoldProductDTO;
import com.softuni.xmlprocessing.domain.entities.dtos.users.*;
import com.softuni.xmlprocessing.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.xmlprocessing.constants.Paths.XML_USERS_WITH_SOLD_PRODUCTS;
import static com.softuni.xmlprocessing.constants.Utils.MODEL_MAPPER;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserSoldProductsDTO>
    findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName() throws IOException {
        final List<UserSoldProductsDTO> users = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserSoldProductsDTO.class))
                .toList();

        return users;
    }

    @Override
    public List<UserWithProductsDTO> usersSoldProducts() throws JAXBException {
        final List<UserWithProductsDTO> users = this
                .userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER
                        .map(user, UserWithProductsDTO.class))
                .toList();

        final UsersWithSoldProductsWrapper usersWrapper =
                new UsersWithSoldProductsWrapper(users);
        List<SoldProductDTO> soldProductDTOS = usersWrapper.getUsers()
                .stream()
                .map(userWithProductsDTO -> MODEL_MAPPER.map(userWithProductsDTO, SoldProductDTO.class))
                .toList();


        final JAXBContext context = JAXBContext
                .newInstance(UsersWithSoldProductsWrapper.class);

        final Marshaller marshaller = context
                .createMarshaller();

        final File file = XML_USERS_WITH_SOLD_PRODUCTS.toFile();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT
                , Boolean.TRUE);
        marshaller.marshal(usersWrapper, file);
        return users;
    }

    @Override
    public UsersWithProductsWrapperDTO usersAndProducts() throws IOException {
        final List<UsersWithProductsDTO> users = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserDTO.class))
                .map(UserDTO::toUsersWithProductsDTO)
                .toList();

        return new UsersWithProductsWrapperDTO(users);
    }

}
