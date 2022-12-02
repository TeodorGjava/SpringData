package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationsUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.example.football.constants.Messages.INVALID_TEAM;
import static com.example.football.constants.Messages.SUCCESSFULLY_ADDED_TEAM;
import static com.example.football.constants.Paths.TEAMS_JSON_PATH;

import java.io.IOException;
import java.nio.file.Files;

//ToDo - Implement all methods
@Service
public class TeamServiceImpl implements TeamService {
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationsUtil validationsUtil;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;

    public TeamServiceImpl(ModelMapper modelMapper, Gson gson, ValidationsUtil validationsUtil, TeamRepository teamRepository, TownRepository townRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationsUtil = validationsUtil;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(TEAMS_JSON_PATH);
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        ImportTeamDTO[] importTeamDTOS = this.gson.fromJson(readTeamsFileContent(), ImportTeamDTO[].class);

        for (ImportTeamDTO teamDTO : importTeamDTOS) {

            boolean isValid = this.validationsUtil.isValid(teamDTO);

            boolean alreadyAdded = this.teamRepository.findFirstByName(teamDTO.getName()).isPresent();
            if (alreadyAdded) {
                isValid = false;
            }
            if (isValid) {
                Team teamToAdd = this.modelMapper.map(teamDTO, Team.class);
                this.townRepository.findFirstByName(teamDTO.getTownName())
                        .ifPresent(teamToAdd::setTown);
                stringBuilder.append(String.format(SUCCESSFULLY_ADDED_TEAM,
                        teamDTO.getName(), teamDTO.getFanBase()));
                this.teamRepository.saveAndFlush(teamToAdd);
            } else {
                stringBuilder.append(String.format(INVALID_TEAM));
            }
        }


        return stringBuilder.toString();
    }
}
