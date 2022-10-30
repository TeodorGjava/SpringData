import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment {

    private static final String PRINT_FORMAT = "%s %s from %s $%.2f%n";

    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();
        final String department = "Research and Development";

        entityManager.createQuery("select e from Employee  e " +
                        "where e.department.name= :dp " +
                        "order by  e.salary asc, e.id", Employee.class)
                .setParameter("dp", department)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(), e.getLastName(),
                        e.getDepartment().getName(), e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
