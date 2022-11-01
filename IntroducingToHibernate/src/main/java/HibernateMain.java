import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateMain {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        Session session;
        try (SessionFactory sessionFactory = cfg.buildSessionFactory()) {
            session = sessionFactory.openSession();

            //begin
            session.beginTransaction();
            Student examplePOJO = new Student();
            //examplePOJO.setName("Tosho");
//insert record to db
            //session.persist(examplePOJO);

            // get Record from db
            Student studentFromDB = session.get(Student.class, 1);
            System.out.println(studentFromDB.getId() + " " + studentFromDB.getName());
            // querying
            List<Student> from_student = session.createQuery("FROM Student AS s WHERE s.name ='Tosho'", Student.class).list();

            for (Student student : from_student) {

                System.out.println(student.getId() + " " + student.getName());
            }
            session.getTransaction().commit();
            session.close();
            //end
            System.out.println("Working!");
        }
    }
}
