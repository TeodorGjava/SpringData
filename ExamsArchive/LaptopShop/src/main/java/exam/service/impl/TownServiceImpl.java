package exam.service.impl;

import exam.model.dtos.xml.ImportTownDTO;
import exam.model.dtos.xml.ImportTownWrapper;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationsUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static exam.constants.Paths.TOWNS_XML_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final XmlParser xmlParser;
    private final TownRepository townRepository;
    private final ValidationsUtil validationsUtil;
    private final ModelMapper mapper;

    @Autowired
    public TownServiceImpl(XmlParser xmlParser, TownRepository townRepository, ValidationsUtil validationsUtil, ModelMapper mapper) {
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.validationsUtil = validationsUtil;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_XML_PATH);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        final StringBuilder stringBuilder = new StringBuilder();

        final ImportTownWrapper townsWrapper = this.xmlParser.fromFile(TOWNS_XML_PATH.toFile(), ImportTownWrapper.class);

        final List<ImportTownDTO> townsDtos = townsWrapper.getTowns();

        for (ImportTownDTO townsDto : townsDtos) {
            boolean isValid = this.validationsUtil.isValid(townsDto);
            if (isValid) {
                final Town townToSave = mapper.map(townsDto, Town.class);
                stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_TOWN,
                        townsDto.getName()));
                this.townRepository.saveAndFlush(townToSave);
            } else {
                stringBuilder.append(String.format(INVALID_TOWN));
            }

        }


        return stringBuilder.toString();
    }
}
