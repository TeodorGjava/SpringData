import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class GetEmployeeWithProject {
    private static final String SELECT_QUERY = "select e from Employee e where e.id = :id";

    public static void main(String[] args) {
        final int id = new Scanner(System.in).nextInt();

        final EntityManager entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        final Employee employee = entityManager.createQuery(SELECT_QUERY, Employee.class)
                .setParameter("id", id)
                .getSingleResult();

        System.out.printf("%s %s %s%n", employee.getFirstName()
                , employee.getLastName(), employee.getDepartment().getName());

        List<Project> toSort = new ArrayList<>(employee.getProjects());

        toSort.sort(Comparator.comparing(Project::getName));
        for (Project project : toSort) {
            System.out.println(project.getName());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

