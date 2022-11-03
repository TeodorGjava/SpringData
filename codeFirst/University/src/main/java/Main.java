import singleton.EntityManagerProvider;

import javax.persistence.EntityManager;

public class Main {
    //sales db
    public static void main(String[] args) {

        final EntityManager university = EntityManagerProvider
                .getManager("university");
        university.getTransaction().begin();

        university.getTransaction().commit();
        university.close();


    }
}
