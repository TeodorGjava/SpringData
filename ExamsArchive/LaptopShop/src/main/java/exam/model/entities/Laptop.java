package exam.model.entities;

import exam.model.enums.WarrantyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity {
    @Column(nullable = false, unique = true)
    @Size(min = 8)
    private String macAddress;
    @Column(nullable = false)
    @DecimalMin("0.1")
    private Double cpuSpeed;
    @Column(nullable = false)
    @Min(8)
    @Max(128)
    private Integer ram;
    @Column(nullable = false)
    @Min(128)
    @Max(1024)
    private Integer storage;

    @Column(nullable = false,columnDefinition = "TEXT")
    @Size(min = 10)
    private String description;
    @Column(nullable = false)
    @DecimalMin("1")
    private Double price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;
    @ManyToOne
    private Shop shop;
}
