package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_ADDED_AGENT;
import static softuni.exam.constants.Paths.PATH_OF_AGENTS_JSON;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final Gson gson;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtils validationUtils, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(PATH_OF_AGENTS_JSON);
    }

    @Override
    public String importAgents() throws IOException {
        final StringBuilder output = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readAgentsFromFile(), ImportAgentDTO[].class))
                .forEach(agentDTO ->
                {
                    boolean isValid = validationUtils.isValid(agentDTO);
                    if (this.agentRepository.findFirstByFirstName(agentDTO.getFirstName()).isPresent()) {
                        isValid = false;
                    }
                    if (isValid) {
                        output.append(String.format(SUCCESSFULLY_ADDED_AGENT,
                                agentDTO.getFirstName(), agentDTO.getLastName()));
                        Agent agentToInsert = this.modelMapper
                                .map(agentDTO, Agent.class);
                        this.townRepository
                                .findFirstByTownName(agentDTO.getTown()).ifPresent(agentToInsert::setTown);
                        this.agentRepository.saveAndFlush(agentToInsert);
                    } else {
                        output.append(String.format(INVALID_AGENT));
                    }
                });
        return output.toString();
    }
}
