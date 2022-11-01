package entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wizards")
public class Wizard {
    @Id
    @Column
    private Long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 60)
    @NotNull
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column
    @NotNull
    private Long age;

    @OneToOne
    @JoinColumn
    private MagicWand magicWand;


    @OneToMany
    @JoinTable(
            name = "wizard_deposits",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "deposit_id")
    )
    private List<Deposit> deposits;

}
