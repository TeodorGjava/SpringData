import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;


public class IncreaseSalaries {

    private static final String GET_EMPLOYEES_FROM_DEPARTMENTS = "select e from Employee e " +
            "where e.department.name in ('Engineering','Tool Design','Marketing','Information Services')";
    private static final String FORMAT = "%s %s ($%.2f)%n";

    public static void main(String[] args) {
        final EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.createQuery(GET_EMPLOYEES_FROM_DEPARTMENTS, Employee.class)
                .getResultList()
                .forEach(e -> e.setSalary(
                        e.getSalary()
                                .multiply(BigDecimal.valueOf(1.12))));

        entityManager.getTransaction().commit();
        entityManager.createQuery(GET_EMPLOYEES_FROM_DEPARTMENTS, Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf(FORMAT
                        , e.getFirstName()
                        , e.getLastName(),
                        e.getSalary()));

        entityManager.close();
    }
}
