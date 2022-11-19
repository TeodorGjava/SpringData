package com.softuni.jsonexercises.constants;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();

    public static void writeJsonIntoFile(List<?> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}
