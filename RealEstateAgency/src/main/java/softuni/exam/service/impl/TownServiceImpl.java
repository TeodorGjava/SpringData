package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_TOWN;
import static softuni.exam.constants.Paths.PATH_OF_TOWNS_JSON;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;

    private final ValidationUtils validationUtils;

    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(PATH_OF_TOWNS_JSON);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder output = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readTownsFileContent(), ImportTownDTO[].class))
                .forEach(townDTO -> {
                    boolean isValid = this.validationUtils.isValid(townDTO);
                    if (this.townRepository.findFirstByTownName(townDTO.getTownName()).isPresent()) {
                        isValid = false;
                    }

                    if (isValid) {
                        output.append(String.format(SUCCESSFULLY_ADDED_TOWN,
                                townDTO.getTownName(), townDTO.getPopulation()));
                        this.townRepository.saveAndFlush(this.modelMapper.map(townDTO, Town.class));
                    } else {
                        output.append(String.format(INVALID_TOWN));
                    }
                });
        return output.toString();
    }
}
