package my.cobeine.sqlava.connection.pool;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public abstract class ConnectionPool<S extends AutoCloseable, R extends AutoCloseable> {

    private final @Getter S source;
    private final ExecutorService pool = Executors.newFixedThreadPool(5);
    public ConnectionPool(S source) {
        this.source = source;
    }

    public abstract R resource();

    public void submit(Runnable o) {
        pool.submit(o);
    }
}
