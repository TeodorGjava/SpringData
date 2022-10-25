import entities.Student;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        MyConnector.createConnection("root", "root", "soft_uni");
        Connection connection = MyConnector.getConnection();

      EntityManager<User> userEntityManager = new EntityManager<>(connection);

      User user = new User("First User", 25, LocalDate.now());

      userEntityManager.persist(user);

      //  EntityManager<Student> studentManager = new EntityManager<>(connection);
      //  Student student = new Student("name");
      //  studentManager.persist(student);

    }
}
