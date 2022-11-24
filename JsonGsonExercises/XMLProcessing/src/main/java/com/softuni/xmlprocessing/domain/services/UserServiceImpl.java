package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.users.*;
import com.softuni.xmlprocessing.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.xmlprocessing.constants.Utils.MODEL_MAPPER;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserWithSoldProductsDTO> findAllByOrderByLastNameAscFirstName() throws JAXBException {
        List<UserWithSoldProductsDTO> userWithSoldProductsDTOS = this.userRepository.findAllByOrderByLastNameAscFirstNameAsc()
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserWithSoldProductsDTO.class)).toList();
        userWithSoldProductsDTOS.forEach(u -> u.getSoldProducts().removeIf(product -> product.getBuyerFirstName() == null
                && product.getBuyerLastName() == null));
        System.out.println();
        return userWithSoldProductsDTOS;
    }
}


