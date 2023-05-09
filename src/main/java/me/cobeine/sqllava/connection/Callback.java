package me.cobeine.sqllava.connection;

import java.sql.SQLException;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Callback<V , T extends Throwable>{
    void oncall(QueryResult<V,T> result) throws SQLException;

    default void call(V r, T t) {
        try {
            oncall(QueryResult.of(r, t));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
