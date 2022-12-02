package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.json.ImportLaptopDTO;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationsUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_LAPTOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_LAPTOP;
import static exam.constants.Paths.LAPTOPS_JSON_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final ModelMapper mapper;
    private final LaptopRepository laptopRepository;
    private final Gson gson;
    private final ValidationsUtil validationsUtil;
    private final ShopRepository shopRepository;

    public LaptopServiceImpl(ModelMapper mapper, LaptopRepository laptopRepository, Gson gson, ValidationsUtil validationsUtil, ShopRepository shopRepository) {
        this.mapper = mapper;
        this.laptopRepository = laptopRepository;
        this.gson = gson;
        this.validationsUtil = validationsUtil;
        this.shopRepository = shopRepository;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOPS_JSON_PATH);
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        ImportLaptopDTO[] importLaptopDTOS = this.gson.fromJson(readLaptopsFileContent(), ImportLaptopDTO[].class);

        for (ImportLaptopDTO importLaptopDTO : importLaptopDTOS) {
            boolean isValid = this.validationsUtil.isValid(importLaptopDTO);
            if (laptopRepository.findFirstByMacAddress(importLaptopDTO.getMacAddress()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                Shop shop = this.shopRepository.findFirstByName(importLaptopDTO.getShop().getName()).get();
                Laptop laptop = this.mapper.map(importLaptopDTO, Laptop.class);
                laptop.setShop(shop);
                this.laptopRepository.saveAndFlush(laptop);

                stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_LAPTOP,
                        laptop.getMacAddress(), laptop.getCpuSpeed(),
                        laptop.getRam(),
                        laptop.getStorage()));
            } else {
                stringBuilder.append(String.format(INVALID_LAPTOP));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String exportBestLaptops() {
        return
                this.laptopRepository.getAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc().stream().map(
                        laptop -> String.format("Laptop - %s%n" +
                                        "*Cpu speed - %.2f%n" +
                                        "**Ram - %d%n" +
                                        "***Storage - %d%n" +
                                        "****Price - %.2f%n" +
                                        "#Shop name - %s" +
                                        "##Town - %s",
                                laptop.getMacAddress(), laptop.getCpuSpeed(),
                                laptop.getRam(),
                                laptop.getStorage(),
                                laptop.getPrice(),
                                laptop.getShop().getName(),
                                laptop.getShop().getTown())
                ).collect(Collectors.joining(System.lineSeparator()));

    }
}
