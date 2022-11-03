import singleton.EntityManagerVol2;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    //FootballDatabase
    public static void main(String[] args) {
        final EntityManager entityManager = EntityManagerVol2
                .getManager("football");
    }
}
