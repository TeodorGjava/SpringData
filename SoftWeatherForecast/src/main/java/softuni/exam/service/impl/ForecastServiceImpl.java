package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastWrapper;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_FORECAST;
import static softuni.exam.constants.Paths.PATH_OF_FORECASTS_XML;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final XmlParser parser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final CityRepository cityRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository, XmlParser parser, ModelMapper modelMapper, ValidationUtils validationUtils, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.parser = parser;
        this.modelMapper = modelMapper;

        this.validationUtils = validationUtils;
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_OF_FORECASTS_XML));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        final StringBuilder output = new StringBuilder();
        final File file = Path.of(PATH_OF_FORECASTS_XML).toFile();

        final ImportForecastWrapper wrapper = parser.fromFile(file, ImportForecastWrapper.class);

        final List<ImportForecastDTO> forecasts = wrapper.getForecasts();

        for (ImportForecastDTO forecast : forecasts) {
            boolean isValid = validationUtils.isValid(forecast);

            if (isValid) {
                if (cityRepository.findFirstById(forecast.getCity()).isPresent()) {
                    final City refCity = cityRepository.findFirstById(forecast.getCity()).get();

                    final Forecast forecastToSave = this.modelMapper.map(forecast, Forecast.class);
                    //TODO::
                    Optional<Forecast> firstByCityId = this.forecastRepository.findFirstByCityId(refCity.getId());
                    if (firstByCityId.isPresent()) {
                        List<Forecast> forecasts1 = firstByCityId.get().getCity().getForecasts().stream().filter(forecast1
                                -> forecast1.getDayOfWeek() != forecastToSave.getDayOfWeek()).toList();
                        if (forecasts1.isEmpty()) {
                            output.append(String.format(INVALID_FORECAST));
                        } else {
                            forecastToSave.setCity(refCity);
                            this.forecastRepository.saveAndFlush(forecastToSave);
                            output.append(String.format(SUCCESSFULLY_ADDED_FORECAST,
                                    forecast.getDayOfWeek(), forecast.getMaxTemperature()));
                        }

                    }
                }
            }
        }
        return output.toString();
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
