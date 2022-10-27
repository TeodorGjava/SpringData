package orm;
//https://discord.gg/kXCuZHqk !! discord groUP SPRING DATA

import orm.Exceptions.ORMException;
import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        //1. find table name
        Field primaryKey = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new ORMException("Entity does not have primary key"));
        primaryKey.setAccessible(true);
        Object val = primaryKey.get(entity);
        String query;
        if (val == null || (long) val <= 0) {
            query = doInsert(entity);
        } else {
            query = doUpdate(entity);
        }
        return this.connection.prepareStatement(query).execute();
    }


    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = this.getTableName(table);

        String query = String.format("select * from %s %s", tableName,
                where == null ? ""
                        : "where " + where);
        ResultSet resultSet = this.connection.prepareStatement(query).executeQuery();

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

        String query = String.format("select * from %s %s limit 1 ", tableName,
                where == null ? "" : "where " + where);

        ResultSet resultSet = this.connection.prepareStatement(query).executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        return this.fillEntity(entityType, resultSet);
    }

    public void doCreate(Class<E> entityClass) {
        final String tableName = getTableName(entityClass);

        //getAllFieldsAndTypesInKeyValuePairs(entityClass);
    }

    //private List<KeyValuePair> getAllFieldsAndTypesInKeyValuePairs(Class<E> entityClass) {
    //   // getDbFieldsWithoutID(entityClass);
    //}

    private E fillEntity(Class<E> entityType, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        E entity = entityType.getDeclaredConstructor().newInstance();

        Field[] declaredFields = entityType.getDeclaredFields();
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
            throw new ORMException("Unsupported type " + field.getType());
        }

    }

    private String doUpdate(E entity) throws IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        //2. get db fields
        String fieldList = this.getDbFieldsWithoutID(entity);
        // //3. get insert Values
        String valueList = this.getValuesWithoutId(entity);

        return String.format("update %s set (%s) = (%s)", tableName, fieldList, valueList);
    }

    private String doInsert(E entity) throws IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());
        //2. get db fields
        String fieldList = this.getDbFieldsWithoutID(entity);
        // //3. get insert Values
        String valueList = this.getValuesWithoutId(entity);

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, fieldList, valueList);
    }

    private String getValuesWithoutId(E entity) throws IllegalAccessException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        List<String> result = new ArrayList<>();
        for (Field field : declaredFields) {
            if (field.getAnnotation(Column.class) == null) {
                continue;
            }

            field.setAccessible(true);
            Object val = field.get(entity);
            result.add("\"" + val.toString() + "\"");
        }
        return String.join(",", result);
    }

    private String getDbFieldsWithoutID(E entity) {
        return Arrays.stream(
                        entity.getClass().getDeclaredFields())
                .filter(f -> f.getAnnotation(Column.class) != null)
                .map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.joining(", "));
    }


    private String getTableName(Class<?> entity) {
        Entity annotation = entity.getAnnotation(Entity.class);
        //if no such annotation
        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation");
        }
        return annotation.name();
    }

    private record KeyValuePair(String key, String value) {
    }
}
