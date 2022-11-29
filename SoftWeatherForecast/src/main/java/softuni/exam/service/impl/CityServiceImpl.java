package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_CITY;
import static softuni.exam.constants.Paths.PATH_OF_CITIES_JSON;


@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_OF_CITIES_JSON));
    }

    @Override
    public String importCities() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        List<ImportCityDTO> cities = Arrays.stream(gson.fromJson(readCitiesFileContent(), ImportCityDTO[].class))
                .toList();
        for (ImportCityDTO city : cities) {
            boolean isValid = this.validationUtils.isValid(city);
            if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()) {
                continue;
            }
            if (isValid) {
                stringBuilder.append(String.format(SUCCESSFULLY_ADDED_CITY, city.getCityName(), city.getPopulation()));
                if (this.countryRepository.findById(city.getCountry()).isPresent()) {
                    City cityToSave = this.modelMapper.map(city, City.class);

                    cityToSave.setCountry(this.countryRepository.findById(city.getCountry()).get());
                    this.cityRepository.save(cityToSave);
                } else {
                    stringBuilder.append("Error").append(System.lineSeparator());
                }
            } else {
                stringBuilder.append(String.format(INVALID_CITY));
            }
        }

        return stringBuilder.toString();
    }
}
