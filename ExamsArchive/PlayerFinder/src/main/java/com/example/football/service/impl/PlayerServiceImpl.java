package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayersWrapper;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationsUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.INVALID_PLAYER;
import static com.example.football.constants.Messages.SUCCESSFULLY_ADDED_PLAYER;
import static com.example.football.constants.Paths.PLAYERS_XML_PATH;

//ToDo - Implement all methods
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationsUtil validationsUtil;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final static String FORMAT = "Player - %s %s%n" +
            "   Position - %s%n" +
            "   Team - %s%n" +
            "   Stadium - %s";

    public PlayerServiceImpl(PlayerRepository playerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationsUtil validationsUtil, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationsUtil = validationsUtil;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(PLAYERS_XML_PATH);
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();

        ImportPlayersWrapper importPlayersWrapper = this.xmlParser
                .fromFile(PLAYERS_XML_PATH.toFile(), ImportPlayersWrapper.class);
        List<ImportPlayerDTO> players = importPlayersWrapper.getPlayers();

        for (ImportPlayerDTO playerDTO : players) {
            boolean isValid = this.validationsUtil.isValid(playerDTO);
            if (this.playerRepository.findFirstByEmail(playerDTO.getEmail()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                Player playerToSave = this.modelMapper.map(playerDTO, Player.class);
                Town playerTown = this.townRepository.findFirstByName(playerDTO.getTown().getName()).get();
                Team playerTeam = this.teamRepository.findFirstByName(playerDTO.getTeam().getName()).get();
                Stat playerStat = this.statRepository.getById(playerDTO.getStat().getId());
                playerToSave.setTown(playerTown);
                playerToSave.setTeam(playerTeam);
                playerToSave.setStats(playerStat);

                output.append(String.format(SUCCESSFULLY_ADDED_PLAYER, playerDTO.getFirstName()
                        , playerDTO.getLastName(), playerDTO.getPosition()));
                this.playerRepository.saveAndFlush(playerToSave);
            } else {
                output.append(String.format(INVALID_PLAYER));
            }
        }

        return output.toString();
    }

    @Override
    public String exportBestPlayers() {

        return this.playerRepository.findAllByBirthDateBetweenOrderByStats_ShootingDescStats_PassingDescStats_EnduranceDescLastName
                        (LocalDate.of(1995, 01, 01), LocalDate.of(2003, 01, 01))
                .stream().map(player -> String.format(FORMAT, player.getFirstName()
                        , player.getLastName(), player.getPosition(), player.getTeam().getName(),
                        player.getTeam().getStadiumName())).collect(Collectors.joining(System.lineSeparator()));


    }
}
