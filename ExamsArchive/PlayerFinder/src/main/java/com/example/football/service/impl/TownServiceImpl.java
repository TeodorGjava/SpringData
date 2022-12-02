package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationsUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

import static com.example.football.constants.Messages.INVALID_TOWN;
import static com.example.football.constants.Messages.SUCCESSFULLY_ADDED_TOWN;
import static com.example.football.constants.Paths.TEAMS_JSON_PATH;
import static com.example.football.constants.Paths.TOWNS_JSON_PATH;


//ToDo - Implement all methods
@Service
public class TownServiceImpl implements TownService {

    private final Gson gson;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationsUtil validationsUtil;
@Autowired
    public TownServiceImpl(Gson gson, TownRepository townRepository, ModelMapper modelMapper, ValidationsUtil validationsUtil) {
        this.gson = gson;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationsUtil = validationsUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return
                Files.readString(TOWNS_JSON_PATH);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        ImportTownDTO[] importTownDTO = this.gson.fromJson(readTownsFileContent(), ImportTownDTO[].class);
        for (ImportTownDTO townDTO : importTownDTO) {

            boolean isValid = validationsUtil.isValid(townDTO);
            if (this.townRepository.findFirstByName(townDTO.getName()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                Town townToAdd = this.modelMapper.map(townDTO, Town.class);
                stringBuilder.append(String.format(SUCCESSFULLY_ADDED_TOWN, townDTO.getName(),
                        townDTO.getPopulation()));
                this.townRepository.saveAndFlush(townToAdd);
            } else {
                stringBuilder.append(String.format(INVALID_TOWN));
            }

        }

        return stringBuilder.toString();
    }
}
