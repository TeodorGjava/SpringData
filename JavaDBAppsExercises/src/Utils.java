import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

enum Utils {
    ;
    static Connection getSqlConnection() throws SQLException {
        Properties props = new Properties();

        props.setProperty(Constants.USER_KEY,Constants.USER_VALUE);
        props.setProperty(Constants.PASSWORD_KEY,Constants.PASSWORD_VALUE);
        return DriverManager.getConnection(Constants.URL_JDBC,props);
    }
}
