package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bet_games")
public class BetGame implements Serializable {
    @Id
    @ManyToOne
    private Game game;

    @Id
    @ManyToOne
    private Bet bet;
    @OneToOne
    private ResultPrediction resultPrediction;

}
