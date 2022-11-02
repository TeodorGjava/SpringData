import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    //sales db
    public static void main(String[] args) {
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("university").createEntityManager();
    }
}
