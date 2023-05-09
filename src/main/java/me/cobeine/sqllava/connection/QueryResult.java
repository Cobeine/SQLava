package me.cobeine.sqllava.connection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@AllArgsConstructor(staticName = "of")
public class QueryResult<V, T extends Throwable> {

    private final V result;
    private final T throwable;

    private Executor<T> throwableExecutor;
    private Executor<V> resultExecutor;

    public Optional<V> getResult() {
        return Optional.ofNullable(result);
    }

    public Optional<T> getException() {
        return Optional.ofNullable(throwable);
    }

    public QueryResult<V, T> executeIfPresent(Executor<V> executor) {
        this.resultExecutor = executor;
        return this;
    }

    public void apply() {
        if (getException().isPresent()) {
            applyThrowable();
            return;
        }
        if (getResult().isPresent())
            applyResult();
    }

    private void applyResult() {
        if (resultExecutor == null) return;
        try {
            if (getResult().isPresent()) {
                resultExecutor.execute(getResult().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QueryResult<V, T> orElse(Executor<T> exception) {
        this.throwableExecutor = exception;
        return this;
    }

    private void applyThrowable() {
        if (throwableExecutor == null) return;
        try {
            if (getException().isPresent()) {
                throwableExecutor.execute(getException().get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface Executor<V> {
        void execute(V v) throws SQLException;
    }

}
