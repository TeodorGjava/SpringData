import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class IncreaseAgeStoredProcedure {
    private static final String UPDATE_MINION_AGE_BY_ID = "call usp_get_older(?)";

    private static final String GET_UPDATED_MINION_INFO_BY_ID = "select name, age from minions where id = ?";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSqlConnection();
        int ID = new Scanner(System.in).nextInt();
        final PreparedStatement updateAgeStatement = connection.prepareStatement(UPDATE_MINION_AGE_BY_ID);
        updateAgeStatement.setInt(1, ID);

        updateAgeStatement.executeUpdate();

        final PreparedStatement getInfoStatement = connection.prepareStatement(GET_UPDATED_MINION_INFO_BY_ID);

        getInfoStatement.setInt(1, ID);

        final ResultSet minionInfoByID = getInfoStatement.executeQuery();

        if (minionInfoByID.next()) {
            final String name = minionInfoByID.getString(Constants.NAME_COLUMN_LABEL);
            final int age = minionInfoByID.getInt(Constants.AGE_COLUMN_LABEL);
            System.out.printf("%s %d", name, age);
        }
        connection.close();
    }
}
