import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ContainsEmployee {
    private static final String QUERY = "select count(e) from Employee e " +
            "where e.firstName =:fn and e.lastName= :ln";

    public static void main(String[] args) {
        EntityManager entityManager = dbConfig.getEntityManager();
        final String[] name = new Scanner(System.in).nextLine().split(" ");

        String firstName = name[0];
        String lastName = name[1];
        entityManager.getTransaction().begin();
        Long matches = entityManager.createQuery(QUERY, Long.class)
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .getSingleResult();
        if (matches == 0) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        entityManager.getTransaction().commit();
        entityManager.close();

    }


}
