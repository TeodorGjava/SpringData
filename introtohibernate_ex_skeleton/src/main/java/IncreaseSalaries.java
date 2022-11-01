import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;


public class IncreaseSalaries {

    private static final String MANIPULATE_EMPLOYEES = "select e from Employee e " +
            "where e.department.name in ('Engineering','Tool Design','Marketing','Information Services')";

    public static void main(String[] args) {
        final EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.createQuery(MANIPULATE_EMPLOYEES, Employee.class)
                .getResultList()
                .forEach(e -> e.setSalary(
                        e.getSalary()
                                .multiply(BigDecimal.valueOf(1.12))));

        entityManager.getTransaction().commit();
        entityManager.createQuery("select e from Employee e" +
                        " where e.department.name in ('Engineering','Tool Design','Marketing','Information Services')", Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s ($%.2f)%n"
                        , e.getFirstName()
                        , e.getLastName(),
                        e.getSalary()));

        entityManager.close();
    }
}
