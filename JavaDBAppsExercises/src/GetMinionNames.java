import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final String GET_MINIONS_NAMES_QUERY = "select m.name, m.age from minions m " +
            "join minions_villains mv on m.id = mv.minion_id " +
            "where mv.villain_id = ?";
    private static final String GET_VILLAIN_NAME_QUERY = "select v.name from villains v " +
            "where v.id = ?;";
    private static final String VILLAIN_FORMAT = "Villain: %s%n";
    private static final String MINION_FORMAT = "%d. %s %d%n";
    private static final String AGE_COLUMN = "age";
    private static final String NO_VILLAIN_FOUND_FORMAT = "No villain with ID %d exists in the database.";


    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSqlConnection();

        final int villainID = new Scanner(System.in).nextInt();

        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_NAME_QUERY);

        villainStatement.setInt(1, villainID);

        final ResultSet villainResultSet = villainStatement.executeQuery();

        if (!villainResultSet.next()) {
            System.out.printf(NO_VILLAIN_FOUND_FORMAT, villainID);
            connection.close();
            return;
        }
        final String villainName = villainResultSet.getString(Constants.NAME_COLUMN_LABEL);
        System.out.printf(VILLAIN_FORMAT, villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(GET_MINIONS_NAMES_QUERY);
        minionsStatement.setInt(1, villainID);

        final ResultSet minionsResultSet = minionsStatement.executeQuery();
        for (int i = 1; minionsResultSet.next(); i++) {

            final String minionName = minionsResultSet.getString(Constants.NAME_COLUMN_LABEL);
            final int minionAge = minionsResultSet.getInt(AGE_COLUMN);

            System.out.printf(MINION_FORMAT, i, minionName, minionAge);

        }
        connection.close();
    }
}

