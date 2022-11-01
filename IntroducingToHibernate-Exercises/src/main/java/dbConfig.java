import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class dbConfig {
    private static final String dbName = "soft_uni";
    public static EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(dbName);

        return entityManagerFactory.createEntityManager();
    }
}
