package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatWrapper;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationsUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.example.football.constants.Messages.INVALID_STAT;
import static com.example.football.constants.Messages.SUCCESSFULLY_ADDED_STAT;
import static com.example.football.constants.Paths.STATS_XML_PATH;

//ToDo - Implement all methods
@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationsUtil validationsUtil;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationsUtil validationsUtil) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationsUtil = validationsUtil;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(STATS_XML_PATH);
    }

    @Override
    public String importStats() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        File file = STATS_XML_PATH.toFile();
        ImportStatWrapper importStatWrapper = this.xmlParser.fromFile(file, ImportStatWrapper.class);

        List<ImportStatDTO> statsDTOS = importStatWrapper.getStats();
        for (ImportStatDTO statsDTO : statsDTOS) {
            boolean isValid = this.validationsUtil.isValid(statsDTO);
            if (this.statRepository.findFirstByEnduranceAndPassingAndShooting(
                    statsDTO.getEndurance(), statsDTO.getPassing(), statsDTO.getShooting()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                final Stat statToInsert = this.modelMapper.map(statsDTO, Stat.class);
                stringBuilder.append(String.format(SUCCESSFULLY_ADDED_STAT,
                        statsDTO.getPassing(), statsDTO.getShooting(), statsDTO.getEndurance()));
                this.statRepository.saveAndFlush(statToInsert);
            } else {
                stringBuilder.append(String.format(INVALID_STAT));
            }

        }
        return stringBuilder.toString();
    }
}
