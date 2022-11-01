package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "deposits")
public class Deposit {
    //    • id – Primary Key (number in range [1, 231-1].

    //    • magic_wand_creator – Text field with max length of 100 symbols
    @Id
    @Column
    private Long id;
    //    • deposit_group - Text field with max length of 20 symbols
    @Column(length = 20)
    private String group;
    //    • deposit_start_date – Date and time field
    @Column()
    private LocalDate startDate;

    //    • deposit_amount – Floating point number field
    @Column
    private double Amount;
    //    • deposit_interest - Floating point number field
    @Column
    private double Interest;
    //    • deposit_charge - Floating point number field
    @Column
    private double Charge;
    //    • deposit_expiration_date – Date and time field
    @Column
    private LocalDate ExpirationDay;
    //    • is_deposit_expired – Boolean field
    @Column
    private boolean isExpired;

}
