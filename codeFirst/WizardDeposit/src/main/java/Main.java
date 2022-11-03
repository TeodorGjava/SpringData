import entities.Wizard;
import singleton.EntityManagerVol2;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        final EntityManager entityManager = EntityManagerVol2
                .getManager("wizards");

        entityManager.getTransaction().begin();
        Wizard wizard = new Wizard();

        //entityManager.persist(wizard);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
