package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.cars.ImportCarDTO;
import softuni.exam.models.dto.xml.cars.ImportCarsWrapper;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationCheck;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static softuni.exam.constants.Messages.CAR_IMPORTED;
import static softuni.exam.constants.Messages.CAR_INVALID;
import static softuni.exam.constants.Paths.CARS_PATH;

@Service
public class CarServiceImpl implements CarService {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final ValidationCheck validations;

    @Autowired
    public CarServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, CarRepository carRepository, ValidationCheck validations) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.validations = validations;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CARS_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();
        final ImportCarsWrapper importCarsWrapper = this.xmlParser.fromFile(CARS_PATH.toFile(), ImportCarsWrapper.class);
        final List<ImportCarDTO> cars = importCarsWrapper.getCars();
        for (ImportCarDTO carDto : cars) {
            boolean isValid = this.validations.isValid(carDto);
            if (this.carRepository.findFirstByPlateNumber(carDto.getPlateNumber()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                Car carToSave = this.modelMapper.map(carDto, Car.class);
                stringBuilder.append(String.format(CAR_IMPORTED, carDto.getCarMake(), carDto.getCarModel()));
                this.carRepository.saveAndFlush(carToSave);
            } else {
                stringBuilder.append(String.format(CAR_INVALID));
            }
        }
        return stringBuilder.toString();
    }
}
