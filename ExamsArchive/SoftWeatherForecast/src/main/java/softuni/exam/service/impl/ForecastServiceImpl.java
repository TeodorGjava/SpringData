package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastWrapper;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DaysOfWeek;
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
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

                    List<Forecast> forecasts1 = refCity.getForecasts().stream()
                            .filter(forecast1
                                    -> forecast1.getDayOfWeek() == forecastToSave.getDayOfWeek()
                                    && forecast1.getCity().getId() == forecast.getCity()).toList();
                    if (!forecasts1.isEmpty() && !refCity.getForecasts().isEmpty()) {
                        output.append(String.format(INVALID_FORECAST));
                    } else {
                        forecastToSave.setCity(refCity);
                        refCity.addForecast(forecastToSave);

                        this.forecastRepository.saveAndFlush(forecastToSave);
                        output.append(String.format(SUCCESSFULLY_ADDED_FORECAST,
                                forecast.getDayOfWeek(), forecast.getMaxTemperature()));
                    }

                } else {
                    output.append(("Invalid city"));
                }

            } else {
                output.append(String.format(INVALID_FORECAST));
            }
        }
        return output.toString();
    }

    @Override
    public String exportForecasts() {
        Set<Forecast> forecasts = forecastRepository.
                findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc
                        (DaysOfWeek.SATURDAY, 150000)
                .orElseThrow(NoSuchElementException::new);


        return forecasts.
                stream()
                .map(forecast -> String.format("City :%s%n" +
                                "-minTemperature: %.2f%n" +
                                "--maxTemperature: %.2f%n" +
                                "---sunrise: %s%n" +
                                "-----sunset: %s", forecast.getCity().getCityName(),
                        forecast.getMinTemperature(), forecast.getMaxTemperature(),
                        forecast.getSunrise(), forecast.getSunset())).collect(Collectors.joining(System.lineSeparator()));
    }
}
