import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class FindByFirstName {
    private static final String FORMAT = "%s %s - %s - ($%.2f)%n";
    private static final String FIND_EMPLOYEE_FIRST_NAME_LIKE = "select e from Employee" +
            " e where e.firstName like :fn";

    public static void main(String[] args) {
        final String firstName = new Scanner(System.in).nextLine();
        final EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(FIND_EMPLOYEE_FIRST_NAME_LIKE, Employee.class)
                .setParameter("fn", firstName + "%")
                .getResultList()
                .forEach(e -> System.out.printf(FORMAT,
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
