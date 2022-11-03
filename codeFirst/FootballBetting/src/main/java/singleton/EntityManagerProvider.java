package singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    private static EntityManager entityManager = null;


    private EntityManagerProvider() {
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
