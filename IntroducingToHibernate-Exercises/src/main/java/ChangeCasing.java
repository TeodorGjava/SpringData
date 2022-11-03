import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

public class ChangeCasing {

    private static final String UPDATE_TOWNS_WITH_LENGTH_MORE_THAN_5 =
            "update Town t set t.name = upper(t.name) where length(t.name) > 5";

    public static void main(String[] args) {

        EntityManager entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        //query update way
        entityManager.createQuery(UPDATE_TOWNS_WITH_LENGTH_MORE_THAN_5, Town.class).executeUpdate();
        //Setter update way
        List<Town> towns = entityManager.createQuery("select t from Town t", Town.class).getResultList();
        for (Town town : towns) {
            final String name = town.getName();
            if (5 >= name.length()) {
                town.setName(name.toUpperCase());
                entityManager.persist(town);
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
