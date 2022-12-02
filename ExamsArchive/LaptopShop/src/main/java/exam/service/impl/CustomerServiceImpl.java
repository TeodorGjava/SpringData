package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.json.ImportCustomersDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationsUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_CUSTOMER;
import static exam.constants.Paths.CUSTOMERS_JSON_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper mapper;
    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ValidationsUtil validationsUtil;
    private final TownRepository townRepository;

    @Autowired
    public CustomerServiceImpl(ModelMapper mapper, CustomerRepository customerRepository, Gson gson, ValidationsUtil validationsUtil, TownRepository townRepository) {
        this.mapper = mapper;
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.validationsUtil = validationsUtil;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMERS_JSON_PATH);
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        ImportCustomersDTO[] importCustomersDTOS = this.gson.fromJson(readCustomersFileContent(), ImportCustomersDTO[].class);

        for (ImportCustomersDTO customersDTO : importCustomersDTOS) {
            boolean valid = this.validationsUtil.isValid(customersDTO);
            if (this.customerRepository.findFirstByFirstName(customersDTO.getFirstName()).isPresent()) {
                valid = false;
            }
            if (valid) {
                Customer customerToSave = this.mapper.map(customersDTO, Customer.class);
                Town town = this.townRepository.findFirstByName(customersDTO.getTown().getName()).get();
                customerToSave.setTown(town);
                stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_CUSTOMER, customersDTO.getFirstName(), customersDTO.getLastName()
                        , customersDTO.getEmail()));
                this.customerRepository.saveAndFlush(customerToSave);
            } else {
                stringBuilder.append(String.format(INVALID_CUSTOMER));
            }
        }


        return stringBuilder.toString();
    }
}
