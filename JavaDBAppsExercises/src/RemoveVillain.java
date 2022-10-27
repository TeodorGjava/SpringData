import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    //Write a program that receives an ID of a villain, deletes him from the database and releases his minions from serving him.
    // As an output print the name of the villain and the number of minions released.
    // Make sure all operations go as planned, otherwise do not make any changes to the database.
    // For the output use the format given in the examples
    private static final String GET_VILLAIN_BY_ID = "select v.name from villains v where v.id =?";
    private static final String GET_COUNT_MINIONS_RELEASED = "select  count(mv.minion_id) " +
            "as count from villains v join minions_villains mv on v.id = mv.villain_id " +
            "where v.id = ?";
    private static final String DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID = "delete from minions_villains mv where mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID = "delete from villains v where v.id = ?";
    private static final String OUTPUT_FORMAT = "%s was deleted%n%d minions released";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSqlConnection();
        final int id = new Scanner(System.in).nextInt();
        final PreparedStatement getVillainStatement = connection.prepareStatement(GET_VILLAIN_BY_ID);

        getVillainStatement.setInt(1, id);
        final ResultSet villainByIdResultSet = getVillainStatement.executeQuery();
        if (!villainByIdResultSet.next()) {
            System.out.println("No such villain was found");
            connection.close();
            return;
        }
        final String villainName = villainByIdResultSet.getString(Constants.NAME_COLUMN_LABEL);

        final PreparedStatement minionsReleasedPreparedStatement = connection.prepareStatement(GET_COUNT_MINIONS_RELEASED);
        minionsReleasedPreparedStatement.setInt(1, id);

        final ResultSet minionsCountResultSet = minionsReleasedPreparedStatement.executeQuery();
        minionsCountResultSet.next();
        final int countReleasedMinions = minionsCountResultSet.getInt(Constants.COUNT_COLUMN_NAME);
        try (final PreparedStatement deleteMinion = connection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAIN_ID);
                final PreparedStatement deleteVillain = connection.prepareStatement(DELETE_VILLAIN_BY_ID)) {
            connection.setAutoCommit(false);

            deleteMinion.setInt(1, id);
            deleteMinion.executeUpdate();

            deleteVillain.setInt(1, id);
            deleteMinion.executeUpdate();
            connection.commit();
            System.out.printf(OUTPUT_FORMAT, villainName, countReleasedMinions);
        } catch (SQLException exception) {
            exception.printStackTrace();
            connection.rollback();
        }
        connection.close();


    }
}

