import entities.Address;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class AddingNewAddressUpdatingEmployee {
    private static final String ADDRESS = "Vitoshka 15";
    private static final String UPDATE_QUERY = "update Employee e set e.address = :newAddress" +
            " where e.lastName = :ln";

    public static void main(String[] args) {
        final EntityManager entityManager = dbConfig.getEntityManager();

        final String lastName = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        Address address = new Address();
        address.setText(ADDRESS);

        entityManager.persist(address);

        final int i = entityManager.createQuery(UPDATE_QUERY)
                .setParameter("newAddress", address)
                .setParameter("ln", lastName)
                .executeUpdate();
        if(i>0) {
            entityManager.getTransaction().commit();
        }else{
            entityManager.getTransaction().rollback();
        }entityManager.close();

        System.out.println(i);
    }
}
