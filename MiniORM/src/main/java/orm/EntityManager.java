package orm;

import orm.Exceptions.ORMException;
import orm.annotations.Column;
import orm.annotations.Entity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
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
        String tableName = this.getTableName(entity);
        //2. get db fields
        String fieldList = this.getDbFieldsWithoutID(entity);
        // //3. get insert Values
        String valueList = this.getValuesWithoutId(entity);

        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, fieldList, valueList);


        return this.connection.prepareStatement(query).execute();

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
            result.add("\""+val.toString()+"\"");
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


    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);
        //if no such annotation
        if (annotation == null) {
            throw new ORMException("Provided class does not have Entity annotation");
        }
        return annotation.name();
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public Iterable<E> findFirst(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> findFirst(Class<E> table, String where) {
        return null;
    }
}
