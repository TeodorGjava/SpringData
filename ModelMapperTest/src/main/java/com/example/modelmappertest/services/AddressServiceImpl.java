package com.example.modelmappertest.services;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.dtos.AddressDTO;
import com.example.modelmappertest.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(AddressDTO data) {
        ModelMapper mapper = new ModelMapper();

        Address address = mapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
