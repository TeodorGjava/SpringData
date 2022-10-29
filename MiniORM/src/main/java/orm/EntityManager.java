package orm;
//https://discord.gg/kXCuZHqk !! discord groUP SPRING DATA

import orm.Exceptions.ORMException;
import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static orm.Exceptions.ExceptionMessages.*;

public class EntityManager<E> implements DbContext<E> {
    private final Connection connection;

    private static final String UPDATE_QUERY_BY_ID_FORMAT = "UPDATE %s e SET %s WHERE e.id = %d";
    private static final String UPDATE_VALUE_FORMAT = "%s = %s";
    private static final String CREATE_VALUE_FORMAT = "%s %s";
    private static final String CREATE_TABLE_QUERY_FORMAT = "create table %s (id int primary key auto_increment, %s );";
    private static final String ADD_COLUMN_QUERY_FORMAT = "ADD COLUMN %s %s";
    private static final String DELETE_QUERY_FORMAT = "DELETE FROM %s WHERE %s = %s";
    private static final String ALTER_TABLE_QUERY_FORMAT = "ALTER TABLE %s %s";
    private static final String SELECT_WHERE_FORMAT = "select * from %s %s";
    private static final String SELECT_WHERE_FIND_FIRST_FORMAT = "select * from %s %s limit 1 ";
    private static final String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String GET_ALL_COLUMNS_NAMES_BY_TABLE_NAME = "select `column_name` from `information_schema`.`columns` " +
            "where `table_schema`='soft_uni' and `column_name` != 'id' and `table_name` = ?";
    private static final String INT = "INT";
    private static final String VARCHAR = "VARCHAR(50)";
    private static final String LOCAL_DATE = "DATE";

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    private List<KeyValuePair> getKeyValuePairs(E entity) {
        final Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class) && f.isAnnotationPresent(Column.class))
                .map(f -> new KeyValuePair(f.getAnnotationsByType(Column.class)[0].name(),
                        mapFieldsToGivenType(f, entity)))
                .collect(Collectors.toList());
    }

    private String mapFieldsToGivenType(Field f, E entity) {
        f.setAccessible(true);

        Object obj = null;
        try {
            obj = f.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj instanceof String || obj instanceof LocalDate
                ? "'" + obj + "'"
                : Objects.requireNonNull(obj).toString();
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        //1. find primaryKey name
        final Field primaryKey = getIdColumn(entity.getClass());
        primaryKey.setAccessible(true);
        final Object val = primaryKey.get(entity);

        if (val == null || (long) val <= 0) {
            return doInsert(entity);
        }
        return doUpdate(entity, primaryKey);


    }


    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final String tableName = this.getTableName(table);

        final String query = String.format(SELECT_WHERE_FORMAT, tableName,
                where == null ? ""
                        : "where " + where);
        final ResultSet resultSet = this.connection.prepareStatement(query).executeQuery();

        List<E> result = new ArrayList<>();

        E lastResult = this.fillEntity(table, resultSet);
        while (lastResult != null) {
            result.add(lastResult);
            lastResult = this.fillEntity(table, resultSet);
        }
        return result;
    }

    @Override
    public E findFirst(Class<E> entityType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(entityType, null);
    }

    @Override
    public E findFirst(Class<E> entityType, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(entityType);

        String query = String.format(SELECT_WHERE_FIND_FIRST_FORMAT, tableName,
                where == null ? "" : "where " + where);

        ResultSet resultSet = this.connection.prepareStatement(query).executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        return this.fillEntity(entityType, resultSet);
    }

    @Override
    public void doAlter(Class<E> entity) throws SQLException {
        final String tableName = getTableName(entity);
        final String currentFields = addColumnsStatementNewFields(entity, tableName);

        String alterQuery = String.format(ALTER_TABLE_QUERY_FORMAT, tableName, currentFields);

        final PreparedStatement alterStatement = connection.prepareStatement(alterQuery);

        alterStatement.execute();
    }

    public void doDelete(E entity) throws SQLException, IllegalAccessException {
        final String tableName = getTableName(entity.getClass());

        final Field idField = getIdColumn(entity.getClass());

        final String idName = getSQLColumnName(idField);

        final Object idValue = getFieldValue(entity, idField);

        PreparedStatement deleteStatement = connection.prepareStatement(String.format(DELETE_QUERY_FORMAT, tableName, idName, idValue));

        deleteStatement.execute();
    }

    private Object getFieldValue(E entity, Field idField) throws IllegalAccessException {
        idField.setAccessible(true);
        return idField.get(entity);
    }

    private String addColumnsStatementNewFields(Class<E> entity, String tableName) throws SQLException {
        final Set<String> sqlCols = getSQLColumnNames(tableName);
        final List<Field> dbFieldsWithoutID = getDbFieldsWithoutID(entity);

        List<String> allNewFields = new ArrayList<>();
        for (Field field : dbFieldsWithoutID
        ) {
            final String fieldName = getSQLColumnName(field);

            if (sqlCols.contains(fieldName)) {
                continue;
            }
            final String sqlType = getSQLType(field.getType());

            final String addStatement = String.format(ADD_COLUMN_QUERY_FORMAT, fieldName, sqlType);
            allNewFields.add(addStatement);
        }
        return String.join(", ", allNewFields);

    }

    private Set<String> getSQLColumnNames(String tableName) throws SQLException {
        Set<String> allFields = new HashSet<>();

        final PreparedStatement getAllFields = connection.prepareStatement(GET_ALL_COLUMNS_NAMES_BY_TABLE_NAME);
        getAllFields.setString(1, tableName);

        ResultSet resultSet = getAllFields.executeQuery();

        while (resultSet.next()) {
            allFields.add(resultSet.getString(1));
        }
        return allFields;
    }

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        final String tableName = getTableName(entityClass);

        final List<KeyValuePair> keyValuePairs = getAllFieldsAndTypesInKeyValuePairs(entityClass);

        final String fieldsWithFormattedTypes = keyValuePairs
                .stream()
                .map(keyValuePair -> String.format(CREATE_VALUE_FORMAT, keyValuePair.key, keyValuePair.value))
                .collect(Collectors.joining(", "));

        final PreparedStatement createTableStatement = connection
                .prepareStatement(String.format(CREATE_TABLE_QUERY_FORMAT,
                        tableName,
                        fieldsWithFormattedTypes));
        createTableStatement.execute();

    }

    private List<KeyValuePair> getAllFieldsAndTypesInKeyValuePairs(Class<E> entityClass) {
        return getDbFieldsWithoutID(entityClass)
                .stream()
                .map(field -> new KeyValuePair(getSQLColumnName(field), getSQLType(field.getType())))
                .toList();
    }

    private String getSQLType(Class<?> type) {
        if (type == int.class || type == Integer.class || type == long.class || type == Long.class) {
            return INT;
        } else if (type == String.class) {
            return VARCHAR;
        } else {
            return LOCAL_DATE;
        }
    }

    private String getSQLColumnName(Field field) {
        return field.getAnnotationsByType(Column.class)[0].name();
    }


    private Field getIdColumn(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(NO_ID_MESSAGE));
    }

    private E fillEntity(Class<E> entityType, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        E entity = entityType.getDeclaredConstructor().newInstance();

        final Field[] declaredFields = entityType.getDeclaredFields();
        for (Field field : declaredFields
        ) {
            if (!field.isAnnotationPresent(Column.class) &&
                    !field.isAnnotationPresent(Id.class)) {
                continue;
            }
            Column columnAnnotation = field.getAnnotation(Column.class);

            final String fieldName = columnAnnotation == null ? field.getName()
                    : columnAnnotation.name();

            final String value = resultSet.getString(fieldName);
            this.fillData(entity, field, value);
        }
        return entity;
    }

    private void fillData(E entity, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);

        if (field.getType() == long.class || field.getType() == Long.class) {
            field.setLong(entity, Long.parseLong(value));
        } else if (field.getType() == int.class || field.getType() == Integer.class) {
            field.setInt(entity, Integer.parseInt(value));
        } else if (field.getType() == LocalDate.class) {
            field.set(entity, LocalDate.parse(value));
        } else if (field.getType() == String.class) {
            field.set(entity, value);
        } else {
            throw new ORMException(UNSUPPORTED_TYPE + field.getType());
        }

    }

    private boolean doUpdate(E entity, Field idColumn) throws IllegalAccessException, SQLException {
        final String tableName = this.getTableName(entity.getClass());
        //2. get db fields
        final List<KeyValuePair> keyValuePairList = this.getKeyValuePairs(entity);
        // //3. get insert Values
        final String updateValues = keyValuePairList.stream()
                .map(KeyValuePair -> String.format(UPDATE_VALUE_FORMAT, KeyValuePair.key, KeyValuePair.value))
                .collect(Collectors.joining(", "));

        final int idValue = Integer.parseInt(idColumn.get(entity).toString());

        final String updateQuery = String.format(UPDATE_QUERY_BY_ID_FORMAT, tableName, updateValues, idValue);
        return connection.prepareStatement(updateQuery).execute();
    }

    private boolean doInsert(E entity) throws SQLException {
        final String tableName = this.getTableName(entity.getClass());
        //2. get db fields
        final List<KeyValuePair> keyValuePairs = getKeyValuePairs(entity);

        final String fieldList = keyValuePairs.stream()
                .map(KeyValuePair::key).collect(Collectors.joining(","));
        // //3. get insert Values
        final String valueList = keyValuePairs.stream()
                .map(KeyValuePair::value).collect(Collectors.joining(","));
        final String insertQuery = String.format(INSERT_QUERY_FORMAT, tableName, fieldList, valueList);
        return connection.prepareStatement(insertQuery).execute();

    }


    private List<Field> getDbFieldsWithoutID(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class) && !f.isAnnotationPresent(Id.class))
                .toList();

    }


    private String getTableName(Class<?> entity) {
        Entity annotation = entity.getAnnotation(Entity.class);
        //if no such annotation
        if (annotation == null) {
            throw new ORMException(NO_ENTITY_EXCEPTION);
        }
        return annotation.name();
    }

    private record KeyValuePair(String key, String value) {
    }
}
