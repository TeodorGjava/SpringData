import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddMinion {
    private static final String GET_TOWN_BY_NAME = "select t.id from towns t where t.name = ?";
    private static final String INSERT_TOWN_INTO_TOWNS = "insert into towns(name) values(?)";

    private static final String TOWN_ADDED_FORMAT = "Town %s was added to the database.%n";
    private static final String GET_VILLAIN_BY_NAME = "select v.id from villains v where v.name = ?";
    private static final String INSERT_VILLAIN_INTO_VILLAINS = "insert into villains(name,evilness_factor) values(?,?)";
    private static final String VILLAIN_ADDED_FORMAT = "Villain %s was added to the database.%n";
    private static final String GET_LAST_MINION_ID = "select m.id from minions m order by m.id desc limit 1";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String COLUMN_LABEL_ID = "id";

    private static final String INSERT_INTO_MINIONS = "insert into minions(name,age,town_id) values(?,?,?)";
    private static final String INSERT_INTO_MINIONS_VILLAINS = "insert into minions_villains(minion_id,villain_id) values(?,?)";
    private static final String FINAL_RESULT_FORMAT = "Successfully added %s to be minion of %s";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSqlConnection();

        Scanner sc = new Scanner(System.in);

        final String[] minionTokens = sc.nextLine().split(" ");

        final String minionName = minionTokens[1];
        final int minionAge = Integer.parseInt(minionTokens[2]);
        final String minionTown = minionTokens[3];

        final String villainName = sc.nextLine().split(" ")[1];

        final int townId = getIdByName(connection,
                List.of(minionTown),
                GET_TOWN_BY_NAME,
                INSERT_TOWN_INTO_TOWNS,
                TOWN_ADDED_FORMAT);
        final int villainId = getIdByName(connection,
                List.of(villainName, EVILNESS_FACTOR),
                GET_VILLAIN_BY_NAME,
                INSERT_VILLAIN_INTO_VILLAINS,
                VILLAIN_ADDED_FORMAT);

        final PreparedStatement minionInsertStatement = connection.prepareStatement(INSERT_INTO_MINIONS);

        minionInsertStatement.setString(1, minionName);
        minionInsertStatement.setInt(2, minionAge);
        minionInsertStatement.setInt(3, townId);

        minionInsertStatement.executeUpdate();

        final PreparedStatement lastMinionStatement = connection.prepareStatement(GET_LAST_MINION_ID);

        final ResultSet lastMinionResultSet = lastMinionStatement.executeQuery();
        if (lastMinionResultSet.next()) {
            final int lastMinion = lastMinionResultSet.getInt(COLUMN_LABEL_ID);

            final PreparedStatement insertMinions_VillainsStatement = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
            insertMinions_VillainsStatement.setInt(1, lastMinion);
            insertMinions_VillainsStatement.setInt(2, villainId);
            insertMinions_VillainsStatement.executeUpdate();

            System.out.printf(FINAL_RESULT_FORMAT, minionName, villainName);
        }
        connection.close();
    }

    private static int getIdByName(
            Connection connection, List<String> args,
            String selectQuery, String insertQuery, String printFormat) throws SQLException {

        final String name = args.get(0);

        final PreparedStatement selectStatement = connection.prepareStatement(selectQuery);

        selectStatement.setString(1, name);

        final ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()) {
            final PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

            for (int i = 1; i <= args.size(); i++) {
                insertStatement.setString(i, args.get(i - 1));
            }

            insertStatement.executeUpdate();

            final ResultSet secondResultSet = selectStatement.executeQuery();
            if (secondResultSet.next()) {
                final int id = secondResultSet.getInt(COLUMN_LABEL_ID);

                System.out.printf(printFormat, name);
            return id;
            }
        }
        return resultSet.getInt(COLUMN_LABEL_ID);

    }
}

