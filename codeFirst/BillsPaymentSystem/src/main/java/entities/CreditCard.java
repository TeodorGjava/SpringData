package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "credit_cards")
public class CreditCard extends BillingDetail {
    @Column
    private String type;
    @Column
    private String expirationMonth;
    @Column
    private String expirationYear;
}
