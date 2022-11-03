import entities.Employee;

import javax.persistence.EntityManager;


public class EmployeesWithSalaryOver50000 {

    public static void main(String[] args) {
        final EntityManager entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("select t.firstName from Employee t where t.salary>50000", String.class)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
