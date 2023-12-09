package my.cobeine.sqlava.connection;

import my.cobeine.sqlava.connection.database.query.QueryHandler;

import java.sql.SQLException;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Callback<V , T extends Throwable>{
    void onCall(QueryHandler<V,T> result);

    default void call(V r, T t) {
        try {
            onCall(QueryHandler.of(r, t));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
