import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("school-db");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student student = new Student();
        student.setName("Gosho");
        entityManager.persist(student);

        entityManager.getTransaction().commit();
        Student student1 = entityManager.find(Student.class, 2);
        System.out.println(student1.getId()+" "+ student1.getName());
        entityManager.remove(student1);

        entityManager.close();
    }
}
