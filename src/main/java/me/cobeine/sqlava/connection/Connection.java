package me.cobeine.sqlava.connection;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Connection<C> {

    C getConnection();

    ConnectionResult connect();

    default void closeConnection() {
        if (getConnection() instanceof Closeable){
            try {
                ((Closeable) getConnection()).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        } else if (getConnection() instanceof AutoCloseable) {
            try {
                ((AutoCloseable) getConnection()).close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }
        throw new UnsupportedOperationException("Cannot close a non-closable connection!");

    }
}
