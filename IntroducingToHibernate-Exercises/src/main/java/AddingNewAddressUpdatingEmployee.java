import entities.Address;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class AddingNewAddressUpdatingEmployee {
    private static final String ADDRESS = "Vitoshka 15";

    public static void main(String[] args) {
        final EntityManager entityManager = dbConfig.getEntityManager();

        final String lastName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        Address address = new Address();
        address.setText(ADDRESS);

        entityManager.persist(address);

        final int i = entityManager.createQuery("update Employee e set e.address = :newAddress" +
                        " where e.lastName = :ln")
                .setParameter("newAddress", address)
                .setParameter("ln", lastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

        System.out.println(i);
    }
}
