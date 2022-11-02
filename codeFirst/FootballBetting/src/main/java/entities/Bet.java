package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bets")
public class Bet extends BaseEntity   {

    @Column
    private Double betMoney;
    @Column
    private LocalDate dateAndTime;

    @ManyToOne
    private User user;


}
