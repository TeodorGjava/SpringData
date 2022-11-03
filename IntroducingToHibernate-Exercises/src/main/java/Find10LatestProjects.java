import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class Find10LatestProjects {
    private static final String FORMAT = "Project name %s%n " +
            " Project Description: %s%n " +
            " Project Start Date: %s%n " +
            " Project End Date: %s%n";
    private static final String QUERY = "select p from Project p order by p.startDate desc";

    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();
        entityManager.getTransaction().begin();
        List<Project> projects = entityManager.createQuery(QUERY, Project.class).getResultList();

        for (int i = 0; i < 10; i++) {
            System.out.printf(FORMAT, projects.get(i).getName(),
                    projects.get(i).getDescription(), projects.get(i).getStartDate(),
                    projects.get(i).getEndDate());
        }
        entityManager.getTransaction().commit();
        entityManager.close();


    }
}
