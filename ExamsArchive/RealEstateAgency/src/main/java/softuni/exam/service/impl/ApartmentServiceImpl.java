package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentDTO;
import softuni.exam.models.dto.ImportApartmentWrapper;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_APARTMENT;
import static softuni.exam.constants.Paths.PATH_OF_APARTMENTS_XML;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtils validationUtils, ModelMapper modelMapper, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(PATH_OF_APARTMENTS_XML);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {

        final StringBuilder output = new StringBuilder();

        final ImportApartmentWrapper wrapper = this.xmlParser.fromFile(PATH_OF_APARTMENTS_XML.toFile(), ImportApartmentWrapper.class);

        final List<ImportApartmentDTO> apartments = wrapper.getApartments();

        for (ImportApartmentDTO apartment : apartments
        ) {
            boolean isValid = validationUtils.isValid(apartment);
            boolean alreadySaved = this.apartmentRepository.findFirstByTownTownNameAndArea(apartment.getTown(), apartment.getArea()).isPresent();
            if (alreadySaved) {
                isValid = false;
            }
            if (isValid) {

                Apartment apartmentToImport = modelMapper.map(apartment, Apartment.class);
                Town town = this.townRepository.findFirstByTownName(apartment.getTown()).get();
                apartmentToImport.setTown(town);
                output.append(String.format(SUCCESSFULLY_ADDED_APARTMENT,
                        apartmentToImport.getApartmentType(), apartmentToImport.getArea()));
                this.apartmentRepository.saveAndFlush(apartmentToImport);
            } else {
                output.append(String.format(INVALID_APARTMENT));
            }
        }
        return output.toString();
    }
}
