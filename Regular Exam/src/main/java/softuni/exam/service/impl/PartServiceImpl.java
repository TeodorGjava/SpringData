package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ImportPartDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationCheck;

import java.io.IOException;
import java.nio.file.Files;

import static softuni.exam.constants.Messages.PART_IMPORTED;
import static softuni.exam.constants.Messages.PART_INVALID;
import static softuni.exam.constants.Paths.PARTS_PATH;

@Service
public class PartServiceImpl implements PartService {
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationCheck validation;
    private final PartRepository partRepository;

    @Autowired
    public PartServiceImpl(ModelMapper modelMapper, Gson gson, ValidationCheck validation, PartRepository partRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validation = validation;
        this.partRepository = partRepository;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PARTS_PATH);
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        ImportPartDTO[] partDTOS = this.gson.fromJson(readPartsFileContent(), ImportPartDTO[].class);
        for (ImportPartDTO partDto : partDTOS
        ) {
            boolean isValid = this.validation.isValid(partDto);

            if (this.partRepository.findFirstByPartName(partDto.getPartName()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                this.partRepository.saveAndFlush(
                        this.modelMapper.map(partDto, Part.class));
                stringBuilder.append(String.format(PART_IMPORTED,
                        partDto.getPartName(), partDto.getPrice()));
            } else {
                stringBuilder.append(String.format(PART_INVALID));
            }
        }

        return stringBuilder.toString();
    }
}
