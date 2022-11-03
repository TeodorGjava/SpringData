import entities.Wizard;
import singleton.EntityManagerProvider;

import javax.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        final EntityManager entityManager = EntityManagerProvider
                .getManager("wizards");

        entityManager.getTransaction().begin();
        Wizard wizard = new Wizard();

        //entityManager.persist(wizard);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
