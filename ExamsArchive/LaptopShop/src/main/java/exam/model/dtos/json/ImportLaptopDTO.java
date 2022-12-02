package exam.model.dtos.json;

import exam.model.enums.WarrantyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportLaptopDTO {
    @Size(min = 8)
    @NotNull
    private String macAddress;
    @DecimalMin("0.1")
    @NotNull
    private Double cpuSpeed;
    @Min(8)
    @Max(128)
    @NotNull
    private Integer ram;
    @Min(128)
    @Max(1024)
    @NotNull
    private Integer storage;
    @Size(min = 10)
    @NotNull
    private String description;
    @Min(1)
    @NotNull
    private Double price;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;
    @NotNull
    private ShopDTO shop;
}
