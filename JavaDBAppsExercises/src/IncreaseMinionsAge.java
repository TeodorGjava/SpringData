import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class IncreaseMinionsAge {

    private static final String UPDATE_MINIONS_AGE_BY_ID = "update minions set age = age+1 where id = ?";
    private static final String UPDATE_MINIONS_NAME_BY_ID = "update minions set name = lower(name) where id = ?";
    private static final String GET_ALL_MINIONS_NAMES_AND_AGES = "select name, age from minions";
    private static final String OUTPUT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSqlConnection();

        Scanner sc = new Scanner(System.in);

        final List<Integer> IDs = Arrays.stream(sc.nextLine().split(" ")).map(Integer::parseInt).toList();


        try (final PreparedStatement agePreparedStatement = connection.prepareStatement(UPDATE_MINIONS_AGE_BY_ID);
             final PreparedStatement namePreparedStatement = connection.prepareStatement(UPDATE_MINIONS_NAME_BY_ID)) {
            connection.setAutoCommit(false);
            for (Integer id : IDs) {
                agePreparedStatement.setInt(1, id);
                agePreparedStatement.executeUpdate();
                namePreparedStatement.setInt(1, id);
                namePreparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        final PreparedStatement getMinionsStatement = connection.prepareStatement(GET_ALL_MINIONS_NAMES_AND_AGES);

        final ResultSet minionsResultSet = getMinionsStatement.executeQuery();

        while (minionsResultSet.next()) {
            final String name = minionsResultSet.getString(Constants.NAME_COLUMN_LABEL);
            final int age = minionsResultSet.getInt(Constants.AGE_COLUMN_LABEL);

            System.out.printf(OUTPUT_FORMAT, name, age);
        }
        connection.close();

    }
}

