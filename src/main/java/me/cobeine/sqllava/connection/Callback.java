package me.cobeine.sqllava.connection;

import java.sql.SQLException;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Callback<V , T extends Throwable>{
    void oncall(QueryHandler<V,T> result) throws SQLException;

    default void call(V r, T t) {
        try {
            oncall(QueryHandler.of(r, t));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
