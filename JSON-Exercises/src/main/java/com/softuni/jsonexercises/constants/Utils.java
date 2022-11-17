package com.softuni.jsonexercises.constants;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static final Gson GSON = new Gson().newBuilder()
            .setPrettyPrinting()
            .create();
}
