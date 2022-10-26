import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    private final static String GET_VILLAINS_NAMES =
            "select v.name, count(distinct mv.minion_id) as minions_count " +
                    "from villains v " +
                    "join minions_villains mv on v.id = mv.villain_id " +
                    "group by mv.villain_id " +
                    "having minions_count > ? " +
                    "order by minions_count";
    private static final int TARGET_MIN_MINIONS = 15;
    private static final String NAME_COLUMN = "name";
    private static final String COUNT_COLUMN = "minions_count";
    private static final String FORMAT = "%s %d";
    static PreparedStatement preparedStatement;

    public static void main(String[] args) throws SQLException {
        final Connection conn = Utils.getSqlConnection();

         preparedStatement = conn.prepareStatement(GET_VILLAINS_NAMES);

        preparedStatement.setInt(1, TARGET_MIN_MINIONS);

        final ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            final String name = rs.getString(NAME_COLUMN);
            final int count = rs.getInt(COUNT_COLUMN);

            System.out.printf(FORMAT, name, count);
        }
        conn.close();
    }
}
