package exam.model.dtos.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TownDTO {
    @NotNull
    @Size(min = 2)
    private String name;
}
