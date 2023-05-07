package me.cobeine.sqllava.connection;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Callback<V , T extends Throwable>{
    void call(V result, T throwable);

}
