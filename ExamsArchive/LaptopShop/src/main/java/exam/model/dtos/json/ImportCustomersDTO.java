package exam.model.dtos.json;

import exam.model.dtos.xml.TownWithNameDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ImportCustomersDTO {
    @NotNull
    @Size(min = 2)
    private String firstName;
    @NotNull
    @Size(min = 2)
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String registeredOn;
    @NotNull
    private TownDTO town;
}
