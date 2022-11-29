package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_COUNTRY;
import static softuni.exam.constants.Paths.PATH_OF_COUNTRIES_JSON;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_OF_COUNTRIES_JSON));
    }

    @Override
    public String importCountries() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFromFile(), ImportCountryDTO[].class))
                .forEach(country -> {
                    boolean isValid = this.validationUtils.isValid(country);
                    if (this.countryRepository.findFirstByCountryName(country.getCountryName()).isPresent()) {
                        isValid = false;
                    }
                    if (isValid) {
                        stringBuilder.append(String.format(SUCCESSFULLY_ADDED_COUNTRY, country.getCountryName(), country.getCurrency()));
                        this.countryRepository.saveAndFlush(this.modelMapper.map(country, Country.class));
                    } else {
                        stringBuilder.append(INVALID_COUNTRY);
                    }
                });

        return stringBuilder.toString();
    }
}
