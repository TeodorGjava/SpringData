package com.example.football.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportTeamDTO {
    @Size(min = 3)
    @NotNull
    private String name;
    @Size(min = 3)
    @NotNull
    private String stadiumName;
    @Min(value = 1000)
    @NotNull
    private Integer fanBase;
    @Size(min = 10)
    @NotNull
    private String history;
    @Size(min = 2)
    @NotNull
    private String townName;
}
