import singleton.EntityManagerVol2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    //sales db
    public static void main(String[] args) {

        final EntityManager university = EntityManagerVol2
                .getManager("university");
        university.getTransaction().begin();

        university.getTransaction().commit();
        university.close();


    }
}
