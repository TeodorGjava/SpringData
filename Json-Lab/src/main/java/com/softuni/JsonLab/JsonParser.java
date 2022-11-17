package com.softuni.JsonLab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.JsonLab.entities.AddressJsonDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class JsonParser implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        AddressJsonDTO addressDTO1 = new AddressJsonDTO("Bulgaria", "Sofia", "Vitoshka");

        //Object To JSON
        String toJson = gson.toJson(addressDTO1);

        System.out.println(toJson);
        //json to Object
        AddressJsonDTO fromJson = gson.fromJson("{\n" +
                "  \"country\": \"Bulgaria\",\n" +
                "  \"town\": \"Sofia\",\n" +
                "  \"street\": \"Vitoshka\"\n" +
                "}", AddressJsonDTO.class);
        System.out.printf("Country: %s%n" +
                        "Town: %s%n" +
                        "Street: %s%n", fromJson.getCountry()
                , fromJson.getTown()
                , fromJson.getStreet());
        // list to json
        AddressJsonDTO addressDTO2 = new AddressJsonDTO("Bulgaria", "Sofia", "Vitoshka");
        AddressJsonDTO addressDTO3 = new AddressJsonDTO("Bulgaria", "Plovdiv", "Pobeda");
        AddressJsonDTO addressDTO4 = new AddressJsonDTO("Bulgaria", "Varna", "Vitosha");
        List<AddressJsonDTO> dtoList = new ArrayList<>();
        dtoList.add(addressDTO2);
        dtoList.add(addressDTO4);
        dtoList.add(addressDTO3);
        System.out.println(gson.toJson(dtoList));

        //Json to list
        String jsonContent = "[\n" +
                "  {\n" +
                "    \"country\": \"Bulgaria\",\n" +
                "    \"town\": \"Sofia\",\n" +
                "    \"street\": \"Vitoshka\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"country\": \"Bulgaria\",\n" +
                "    \"town\": \"Varna\",\n" +
                "    \"street\": \"Vitosha\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"country\": \"Bulgaria\",\n" +
                "    \"town\": \"Plovdiv\",\n" +
                "    \"street\": \"Pobeda\"\n" +
                "  }\n" +
                "]";
        Arrays.stream(gson.fromJson(jsonContent, AddressJsonDTO[].class)).forEach(address -> System.out.println(address.getTown()));
    }
}
