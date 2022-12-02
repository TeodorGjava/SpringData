package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportCountryDTO {
    @Size(min = 2, max = 60)
    @NotNull

    private String countryName;
    @Size(min = 2, max = 60)
    @NotNull
    private String currency;
}
