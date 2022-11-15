import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressesWithEmployeeCount {
    private static final String GET_ALL_ADDRESSES_ORDER_BY_EMPLOYEE_SIZE_DESC = "select a from Address a order by a.employees.size desc";
    public static void main(String[] args) {

        final EntityManager manager = dbConfig.getEntityManager();

        manager.createQuery(GET_ALL_ADDRESSES_ORDER_BY_EMPLOYEE_SIZE_DESC,Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(System.out::println);

        manager.close();
    }
}