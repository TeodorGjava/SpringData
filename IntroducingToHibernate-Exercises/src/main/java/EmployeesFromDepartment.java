import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment {

    private static final String PRINT_FORMAT = "%s %s from %s $%.2f%n";
    private static final String DEPARTMENT = "Research and Development";
    private static final String QUERY = " select e from Employee  e " +
            "                        where e.department.name= :dp " +
            "                        order by  e.salary asc, e.id";

    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(QUERY, Employee.class)
                .setParameter("dp", DEPARTMENT)
                .getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(), e.getLastName(),
                        e.getDepartment().getName(), e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
