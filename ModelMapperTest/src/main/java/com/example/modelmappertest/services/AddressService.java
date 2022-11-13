package com.example.modelmappertest.services;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.dtos.AddressDTO;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address create(AddressDTO data);
}
