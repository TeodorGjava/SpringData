package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportMechanicDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.ValidationCheck;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static softuni.exam.constants.Messages.MECHANIC_IMPORTED;
import static softuni.exam.constants.Messages.MECHANIC_INVALID;
import static softuni.exam.constants.Paths.MECHANICS_PATH;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationCheck validations;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, ModelMapper modelMapper, Gson gson, ValidationCheck validations) {
        this.mechanicRepository = mechanicRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validations = validations;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(MECHANICS_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        final StringBuilder str = new StringBuilder();

        final ImportMechanicDTO[] mechanicDTOS = this.gson.fromJson(readMechanicsFromFile(), ImportMechanicDTO[].class);

        for (ImportMechanicDTO mechanicDTO : mechanicDTOS) {
            boolean isValid = this.validations.isValid(mechanicDTO);
            if (this.mechanicRepository.findFirstByEmail(mechanicDTO.getEmail()).isPresent()) {
                isValid = false;
            }
            Optional<Mechanic> mechanicByFirstName = this.mechanicRepository.findFirstByFirstName(mechanicDTO.getFirstName());
            if (mechanicByFirstName.isPresent()) {
                isValid = false;
            }
            if (this.mechanicRepository.findFirstByPhone(mechanicDTO.getPhone()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                str.append(String.format(MECHANIC_IMPORTED, mechanicDTO.getFirstName(),
                        mechanicDTO.getLastName()));
                this.mechanicRepository.saveAndFlush(this.modelMapper.map(mechanicDTO, Mechanic.class));
            } else {
                str.append(String.format(MECHANIC_INVALID));
            }

        }
        return str.toString();
    }
}
