import entities.Account;
import entities.Student;
import entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        MyConnector.createConnection("root", "root", "soft_uni");
        Connection connection = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

        // User user = new User("Third User", 25, LocalDate.now());
//
        // userEntityManager.persist(user);
//
        //  EntityManager<Student> studentManager = new EntityManager<>(connection);
        //  Student student = new Student("Pesho");
        //  studentManager.persist(student);

        //User first = userEntityManager.findFirst(User.class);
        //System.out.println(first.getUsername());
        //System.out.println("Student identity: " + first.getId() + "\nName: " + first.getUsername() + "\nRegistered "
        //        + first.getRegistration() + "\n" + first.getAge() + " Years old.");
//
        //userEntityManager
        //        .find(User.class, "age>17 and registration_date > 2021-12-12")
        //        .forEach(user -> System.out.println(user.toString()));
        EntityManager<Account> accountEntityManager = new EntityManager<>(connection);

       //accountEntityManager.doCreate(Account.class);
        Account account = new Account("Teodor",LocalDate.now(),24);
        accountEntityManager.persist(account);
    }
}
