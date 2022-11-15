import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns {

    private final static String FORMAT = "%d address%s in %s deleted%n";
    private final static String SELECT_ADDRESSES = "select a from Address a where a.town.name = :tName";
    private final static String SELECT_TOWN = "select t from Town t where t.name = :tName";


    public static void main(String[] args) {

        final EntityManager entityManager = dbConfig.getEntityManager();
        final String townName = new Scanner(System.in).nextLine().trim();

        final Town town = entityManager.createQuery(SELECT_TOWN, Town.class)
                .setParameter("tName", townName).getSingleResult();
        final List<Address> addressList = entityManager.createQuery
                        (SELECT_ADDRESSES, Address.class)
                .setParameter("tName", townName)
                .getResultList();

        entityManager.getTransaction().begin();

        addressList.forEach(a -> {
            for (Employee e : a.getEmployees()
            ) {
                e.setAddress(null);
            }
            a.setTown(null);
            entityManager.remove(a);
        });
        entityManager.remove(town);

        entityManager.getTransaction().commit();

        System.out.printf(FORMAT,
                addressList.size(),
                addressList.size() != 1 ? "es" : "",
                town.getName());

        entityManager.close();
    }
}
