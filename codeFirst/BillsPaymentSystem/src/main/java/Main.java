import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    //FootballDatabase
    public static void main(String[] args) {
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("bills_payment").createEntityManager();
    }
}
