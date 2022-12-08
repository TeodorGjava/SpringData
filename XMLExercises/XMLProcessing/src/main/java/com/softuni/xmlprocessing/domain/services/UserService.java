package com.softuni.xmlprocessing.domain.services;


import com.softuni.xmlprocessing.domain.entities.dtos.users.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserWithSoldProductsDTO> findAllByOrderByLastNameAscFirstName() throws JAXBException;
}
