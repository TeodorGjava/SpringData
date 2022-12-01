package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportOfferDTO;
import softuni.exam.models.dto.ImportOfferWrapper;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.enums.ApartmentType;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_OFFER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_OFFER;
import static softuni.exam.constants.Paths.PATH_OF_OFFERS_XML;

@Service
public class OfferServiceImpl implements OfferService {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final OfferRepository offerRepository;
    private final ValidationUtils validationUtils;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    public OfferServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, OfferRepository offerRepository, ValidationUtils validationUtils, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.offerRepository = offerRepository;
        this.validationUtils = validationUtils;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(PATH_OF_OFFERS_XML);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder output = new StringBuilder();

        //    • If agent with the given name doesn’t already exist in the DB return "Invalid offer".
        //    • The provided apartment ids will always be valid.
        //    • Format the price to the second digit after the decimal point.
        ImportOfferWrapper importOfferWrapper = this.xmlParser.fromFile(PATH_OF_OFFERS_XML.toFile(), ImportOfferWrapper.class);
        List<ImportOfferDTO> offerDTOS = importOfferWrapper.getOffers();
        for (ImportOfferDTO offerDTO : offerDTOS) {
            boolean valid = this.validationUtils.isValid(offerDTO);
            if (valid) {
                Offer offerToImport = this.modelMapper.map(offerDTO, Offer.class);

                Optional<Agent> agentByName = this.agentRepository.findFirstByFirstName(offerDTO.getAgent().getName());
                Long id = offerDTO.getApartmentIdDTO().getId();
                Optional<Apartment> apartmentById = this.apartmentRepository.findFirstById(id);
                boolean arePresent = apartmentById.isPresent() && agentByName.isPresent();
                if (arePresent) {
                    offerToImport.setAgent(agentByName.get());
                    offerToImport.setApartment(apartmentById.get());
                    output.append(String.format(SUCCESSFULLY_ADDED_OFFER, offerToImport.getPrice()));
                    this.offerRepository.saveAndFlush(offerToImport);
                }
            } else {
                output.append(String.format(INVALID_OFFER));
            }
        }

        return output.toString();
    }

    @Override
    public String exportOffers() {
        return this.offerRepository.findAllByApartmentApartmentTypeOrderByApartment_AreaDescPriceAsc(ApartmentType.three_rooms)
                .stream()
                .map(offer -> String.format("Agent %s %s with offer №%s:%n" +
                                "-Apartment area: %.2f%n" +
                                "--Town: %s%n" +
                                "---Price: %.2f$", offer.getAgent().getFirstName(), offer.getAgent().getLastName(),
                        offer.getId(),
                        offer.getApartment().getArea(), offer.getApartment().getTown().getTownName(),
                        offer.getPrice())).collect(Collectors.joining(System.lineSeparator()));

    }
}
