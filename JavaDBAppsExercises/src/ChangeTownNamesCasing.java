import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {

    private static final String GET_TOWNS_QUERY = "select t.name from towns t " +
            "where t.country = ?";

    private static final String UPDATE_TOWNS_TO_UPPER_CASE = "update towns set name = upper(name) " +
            "where country = ?";
    private static final String GET_TOWNS_COUNT_QUERY = "select count(*) as 'count' from towns where country = ?";


    private static final String COUNT_AFFECTED_TOWNS_FORMAT = "%d town names were affected.%n";
    private static final String NO_SUCH_COUNTRY_OR_TOWNS_MESSAGE = "No town names were affected.";

    public static void main(String[] args) throws SQLException {
        final Scanner sc = new Scanner(System.in);

        String countyName = sc.nextLine();

        final Connection connection = Utils.getSqlConnection();

        final PreparedStatement getCountryTowns = connection.prepareStatement(GET_TOWNS_QUERY);
        getCountryTowns.setString(1, countyName);
        final ResultSet resultSetTowns = getCountryTowns.executeQuery();

        if (!resultSetTowns.next()) {
            System.out.println(NO_SUCH_COUNTRY_OR_TOWNS_MESSAGE);
            connection.close();
            return;
        }
        final PreparedStatement migrateToUpperCase = connection.prepareStatement(UPDATE_TOWNS_TO_UPPER_CASE);

        migrateToUpperCase.setString(1, countyName);

        migrateToUpperCase.executeUpdate();

        final PreparedStatement getCountOfUpdatedTowns = connection.prepareStatement(GET_TOWNS_COUNT_QUERY);

        getCountOfUpdatedTowns.setString(1, countyName);

        final ResultSet countAffectedTownsResultSet = getCountOfUpdatedTowns.executeQuery();
        if (countAffectedTownsResultSet.next()) {
            int countTowns = countAffectedTownsResultSet.getInt(Constants.COUNT_COLUMN_NAME);

            System.out.printf(COUNT_AFFECTED_TOWNS_FORMAT, countTowns);
        }
        final PreparedStatement getUpdatedTownsNames = connection.prepareStatement(GET_TOWNS_QUERY);
        getUpdatedTownsNames.setString(1, countyName);

        final ResultSet updatedTownsResultSet = getUpdatedTownsNames.executeQuery();

        final List<String> townNames = new ArrayList<>();
        while (updatedTownsResultSet.next()) {
            String town = updatedTownsResultSet.getString(Constants.NAME_COLUMN_LABEL);
            townNames.add(town);
        }
        System.out.println(townNames);
        connection.close();
    }
}

