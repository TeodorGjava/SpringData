import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EmployeesWithSalaryOver50000 {
        private static final String GET_EMPLOYEES_WITH_SALARIES_OVER5000 = "select t.firstName from Employee t where t.salary>50000";
    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(GET_EMPLOYEES_WITH_SALARIES_OVER5000, String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
