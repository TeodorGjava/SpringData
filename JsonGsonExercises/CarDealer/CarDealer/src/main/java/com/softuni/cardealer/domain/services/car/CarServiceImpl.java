package com.softuni.cardealer.domain.services.car;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.Part;
import com.softuni.cardealer.domain.entities.dtos.cars.CarWithPartsDTO;
import com.softuni.cardealer.domain.entities.dtos.cars.ExportCarDTO;
import com.softuni.cardealer.domain.entities.dtos.parts.PartDTO;
import com.softuni.cardealer.domain.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.softuni.cardealer.domain.constants.Paths.*;
import static com.softuni.cardealer.domain.constants.Utils.MODEL_MAPPER;
import static com.softuni.cardealer.domain.constants.Utils.writeJsonIntoFile;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<ExportCarDTO> findAllOrderByModelAndTravelledDistance() throws IOException {
        List<ExportCarDTO> allOrderByModelAndTravelledDistance = this.carRepository
                .findAllOrderByModelAndTravelledDistance()
                .stream()
                .map(car -> MODEL_MAPPER.map(car, ExportCarDTO.class)).toList();

        writeJsonIntoFile(allOrderByModelAndTravelledDistance, JSON_CARS_FROM_TOYOTA_PATH);
        return allOrderByModelAndTravelledDistance;
    }

    @Override
    public List<CarWithPartsDTO> findAllCarsAndParts() throws IOException {
        List<CarWithPartsDTO> carWithPartsDTOS = this.carRepository.findAllCarsAndParts()
                .stream().map(car -> {
                    Set<PartDTO> parts = car.getParts();

                    return new CarWithPartsDTO(parts, car.getMake(), car.getModel(), car.getTravelledDistance());
                })
                .toList();
        writeJsonIntoFile(carWithPartsDTOS, JSON_CARS_AND_PARTS_PATH);
        return carWithPartsDTOS;
    }
}
