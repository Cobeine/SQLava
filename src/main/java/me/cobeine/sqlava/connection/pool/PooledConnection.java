package me.cobeine.sqlava.connection.pool;

import me.cobeine.sqlava.connection.Connection;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface PooledConnection<S extends AutoCloseable, R extends AutoCloseable> extends Connection<S> {


    ConnectionPool<S, R> getPool();

}
