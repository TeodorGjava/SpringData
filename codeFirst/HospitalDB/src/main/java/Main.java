import singleton.EntityManagerProvider;

import javax.persistence.EntityManager;

public class Main {
    //FootballDatabase
    public static void main(String[] args) {
        final EntityManager entityManager = EntityManagerProvider
                .getManager("hospital");
    }
}
