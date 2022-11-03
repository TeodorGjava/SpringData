package singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerVol2 {
    private static EntityManager entityManager = null;


    private EntityManagerVol2() {
    }

    public static EntityManager getManager(String persistenceUnitName) {
        if (entityManager == null) {
            entityManager =
                    Persistence.createEntityManagerFactory(persistenceUnitName)
                            .createEntityManager();
        }
        return entityManager;
    }

}
