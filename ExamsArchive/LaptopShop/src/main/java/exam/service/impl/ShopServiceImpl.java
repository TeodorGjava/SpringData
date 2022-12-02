package exam.service.impl;

import exam.model.dtos.xml.ImportShopDTO;
import exam.model.dtos.xml.ImportShopWrapper;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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

import static exam.constants.Messages.INVALID_SHOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED_SHOP;
import static exam.constants.Paths.SHOPS_XML_PATH;

@Service
public class ShopServiceImpl implements ShopService {
    private final XmlParser xmlParser;
    private final ShopRepository shopRepository;
    private final ValidationsUtil validationsUtil;
    private final ModelMapper mapper;
    private final TownRepository townRepository;

    @Autowired
    public ShopServiceImpl(XmlParser xmlParser, ShopRepository shopRepository, ValidationsUtil validationsUtil, ModelMapper mapper, TownRepository townRepository) {
        this.xmlParser = xmlParser;
        this.shopRepository = shopRepository;

        this.validationsUtil = validationsUtil;
        this.mapper = mapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOPS_XML_PATH);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        final StringBuilder stringBuilder = new StringBuilder();
        final ImportShopWrapper shopsWrapper = this.xmlParser.fromFile(SHOPS_XML_PATH.toFile(), ImportShopWrapper.class);
        final List<ImportShopDTO> shopDTOS = shopsWrapper.getShops();

        for (ImportShopDTO shopDTO : shopDTOS) {
            boolean isValid = this.validationsUtil.isValid(shopDTO);
            if (this.shopRepository.findFirstByName(shopDTO.getName()).isPresent()) {
                isValid = false;
            }
            if (isValid) {
                final Shop shopToImport = mapper.map(shopDTO, Shop.class);
                final Town town = this.townRepository.findFirstByName(shopDTO.getTown().getName()).get();
                shopToImport.setTown(town);
                stringBuilder.append(String.format(SUCCESSFULLY_IMPORTED_SHOP,
                        shopDTO.getName(), shopDTO.getIncome()));
                this.shopRepository.saveAndFlush(shopToImport);
            } else {
                stringBuilder.append(String.format(INVALID_SHOP));
            }
        }

        return stringBuilder.toString();
    }
}
