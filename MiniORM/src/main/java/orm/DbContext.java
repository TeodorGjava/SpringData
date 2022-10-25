package orm;

import java.sql.SQLException;

public interface DbContext<E> {
    boolean persist(E entity) throws SQLException, IllegalAccessException;

    Iterable<E> find(Class<E> table);

    Iterable<E> find(Class<E> table,String where);

    Iterable<E> findFirst(Class<E> table);
    Iterable<E> findFirst(Class<E> table,String where);
}
