import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintAllMinionNames {
    private static final String GET_FIRST_MINION = "select m.name from minions m " +
            "order by m.id limit 25";
    private static final String GET_LAST_MINION = "select m.name from minions m " +
            "order by m.id desc limit 25";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSqlConnection();

        final PreparedStatement ascendingMinions = connection.prepareStatement(GET_FIRST_MINION);
        final PreparedStatement descendingMinions = connection.prepareStatement(GET_LAST_MINION);

        final ResultSet ascendingResultSet = ascendingMinions.executeQuery();
        final ResultSet descendingResultSet = descendingMinions.executeQuery();

        while (ascendingResultSet.next() && descendingResultSet.next()) {
            System.out.println(ascendingResultSet.getString(Constants.NAME_COLUMN_LABEL));
            System.out.println(descendingResultSet.getString(Constants.NAME_COLUMN_LABEL));
        }
        connection.close();

    }

}

