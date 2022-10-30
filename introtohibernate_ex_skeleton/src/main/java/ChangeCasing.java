import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ChangeCasing {
    private static final String dbName = "soft_uni";
   // private static final String UPDATE_TOWNS_WITH_LENGTH_MORE_THAN_5 =
   //         "update Town t set t.name = upper(t.name) where length(t.name) > 5";

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(dbName);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query upperTowns = entityManager.createQuery("select t from Town t",Town.class);
        upperTowns.executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
