package softuni.exam.models.dto.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportMechanicDTO {
    //"email":"firstName":"lastName":"phone":
    @NotNull
    @Size(min = 2)
    private String email;
    @Size(min = 2)
    @NotNull
    private String firstName;
    @Size(min = 2)
    @NotNull
    private String lastName;
    @Size(min = 2)
    private String phone;
}
