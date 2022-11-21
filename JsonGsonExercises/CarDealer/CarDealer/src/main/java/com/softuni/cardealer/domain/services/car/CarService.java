package com.softuni.cardealer.domain.services.car;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.dtos.cars.CarWithPartsDTO;
import com.softuni.cardealer.domain.entities.dtos.cars.ExportCarDTO;

import java.io.IOException;
import java.util.List;

public interface CarService {

    List<ExportCarDTO> findAllOrderByModelAndTravelledDistance() throws IOException;

    List<CarWithPartsDTO> findAllCarsAndParts() throws IOException;
}
