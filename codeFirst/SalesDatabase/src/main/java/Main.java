import singleton.EntityManagerProvider;

import javax.persistence.EntityManager;

public class Main {
    //sales db
    public static void main(String[] args) {
        final EntityManager entityManager = EntityManagerProvider
                .getManager("sales");

    }
}
