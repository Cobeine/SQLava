package me.cobeine.sqlava.connection.database.query;

import me.cobeine.sqlava.connection.Callback;
import me.cobeine.sqlava.connection.database.MySQLConnection;
import org.jetbrains.annotations.NotNull;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class PreparedQuery {
    private final MySQLConnection sqlConnection;
    private Connection connection;
    private PreparedStatement statement;
    boolean batched;
    public PreparedQuery(@NotNull MySQLConnection sqlConnection, @NotNull String buildQuery) {
        this.sqlConnection = sqlConnection;
        try {
            this.connection = sqlConnection.getConnection().getConnection();
            this.statement = connection.prepareStatement(buildQuery);
        } catch (SQLException e) {
            sqlConnection.getLogger().severe(String.format("Failed to prepare statement of query '%s': %s", buildQuery, e));
        }
    }
    public PreparedQuery setParameter(int index, Object value) {

        try {
            if (statement == null)
             return this;

            if (value instanceof String) statement.setString(index, (String) value);
            else statement.setObject(index, value);
        } catch (Exception ignored) {
        }
        return this;
    }

    public int[] executeBatch() throws SQLException {
        try {
            return statement.executeBatch();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.commit();
                connection.close();
            }
        }
    }

    public void executeBatchAsync() {
        executeBatchAsync(null);
    }

    public void executeBatchAsync(final Callback<int[], SQLException> callback) {
        sqlConnection.getPool().submit(() -> {
            try {
                int[] rowsChanged = executeBatch();
                if (callback != null) {
                    callback.call(rowsChanged, null);
                }
            } catch (SQLException e) {
                if (callback != null) {
                    callback.call(null, e);
                }
            }
        });
    }

    public int executeUpdate() throws SQLException {
        try {
            return statement.executeUpdate();

        } finally {

            if (statement != null && !statement.isClosed())
                statement.close();

            if (connection != null)
                connection.close();

        }
    }

    public int executeUpdateWithKeys() throws SQLException {
        try {
            String stm = statement.toString();
            String sql = stm.substring(stm.indexOf("INSERT"));
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet == null || !resultSet.next())
                return 0;
            return resultSet.getInt(1);
        } finally {

            if (statement != null)
                statement.close();

            if (connection != null)
                connection.close();

        }
    }

    public ResultSet executeQuery() throws SQLException {
        CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();

        try (ResultSet resultSet = statement.executeQuery()) {

            rowSet.populate(resultSet);

        } finally {

            if (statement != null && !statement.isClosed())
                statement.close();

            if (connection != null)
                connection.close();

        }
        return rowSet;
    }

    public void executeUpdateAsync(final Callback<Integer, SQLException> callback) {
        sqlConnection.getPool().submit(() -> {

            try {
                int rowsChanged = executeUpdate();
                if (callback != null)
                    callback.call(rowsChanged, null);

            } catch (SQLException e) {
                if (callback != null)
                    callback.call(0, e);
                else
                    e.printStackTrace();
            }
        });
    }
    private void addBatch() throws SQLException {
        if (batched)
            return;
        if (connection.getAutoCommit()) {
            connection.setAutoCommit(false);
        }
        statement.addBatch();
        batched = true;
    }

    public void addBatch(String s) throws SQLException {
        addBatch();
        statement.addBatch(s);
    }

    public void executeUpdateAsyncWithGeneratedKeys( Callback<Integer, SQLException> callback) {
        sqlConnection.getPool().submit(() -> {

            try {
                int id = executeUpdateWithKeys();
                if (callback != null)
                    callback.call(id, null);

            } catch (SQLException e) {
                if (callback != null)
                    callback.call(0, e);
                else
                    e.printStackTrace();
            }
        });
    }

    public void executeUpdateAsync() {
        executeUpdateAsync(null);
    }
    public void executeQueryAsync(final Callback<ResultSet, SQLException> callback) {
        sqlConnection.getPool().submit(() -> {
            try {
                ResultSet rs = executeQuery();
                callback.call(rs, null);
            } catch (SQLException e) {
                callback.call(null, e);
            }
        });
    }
    @SuppressWarnings("unused")
    public void rollback() throws SQLException {
        if (connection != null)
            connection.rollback();
    }
}
