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
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.VALID_COUNTRY_FORMAT;
import static softuni.exam.constants.Paths.URL_COUNTRIES_JSON;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    private final ValidationUtils validationUtils;
    private final Gson gson;

    private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtils validationUtils, Gson gson, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.validationUtils = validationUtils;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(URL_COUNTRIES_JSON));
    }

    @Override
    public String importCountries() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        List<Country> countries = Arrays.stream(gson.fromJson(readCountriesFromFile(), ImportCountryDTO[].class))
                .filter(country -> {
                    boolean isValid = this.validationUtils.isValid(country);
                    if(this.countryRepository.getCountryByCountryName(country.getCountryName()).isPresent()){
                        isValid=false;
                    }
                    if (isValid) {
                        stringBuilder.append(String.format(VALID_COUNTRY_FORMAT,
                                country.getCountryName(), country.getCurrency()));
                    this.countryRepository.saveAndFlush(this.modelMapper.map(country,Country.class));
                    } else {
                        stringBuilder.append(String.format(INVALID_COUNTRY));
                    }
                    return isValid;
                }).map(country -> this.modelMapper.map(country, Country.class)).toList();

        System.out.println();
        return stringBuilder.toString();
    }


}
