package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.PATH_OF_CITIES_JSON;
import static softuni.exam.constants.Paths.PATH_OF_COUNTRIES_JSON;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    public CityServiceImpl(CityRepository cityRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
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

        Arrays.stream(gson.fromJson(readCitiesFileContent(), ImportCityDTO[].class))
                .forEach(city -> {
                    boolean isValid = this.validationUtils.isValid(city);

                    if (this.cityRepository.findFirstByCityName(city.getCityName()).isPresent()) {
                        isValid = false;
                    }
                    if (isValid) {
                        stringBuilder.append(String.format(SUCCESSFULLY_ADDED_CITY,
                                city.getCityName(), city.getPopulation()));
                        
                        this.cityRepository.saveAndFlush(this.modelMapper.map(city, City.class));
                    } else {
                        stringBuilder.append(INVALID_CITY);
                    }
                });
        System.out.println();
        return stringBuilder.toString();
    }
}
