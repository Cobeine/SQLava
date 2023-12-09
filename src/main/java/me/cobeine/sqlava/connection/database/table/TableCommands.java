package me.cobeine.sqlava.connection.database.table;

import me.cobeine.sqlava.connection.Callback;
import me.cobeine.sqlava.connection.database.MySQLConnection;
import me.cobeine.sqlava.connection.database.query.PreparedQuery;

import java.sql.SQLException;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public final class TableCommands {
    private final MySQLConnection connection;
    public TableCommands(MySQLConnection connection) {
        this.connection = connection;
    }

    public void createTables(Callback<int[], SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement(first.toString());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch(table.toString());
        }
        query.executeBatchAsync(callback);
    }

    public void createTables(Table... tables) throws SQLException {
        createTables(null, tables);
    }

    public void createTable(Table table, Callback<Integer, SQLException> callback) {
        PreparedQuery query = prepareStatement(table.toString());
        query.executeUpdateAsync(callback);
    }

    public void createTable(Table table) {
        createTable(table, null);
    }

    public void dropTables(Callback<int[], SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement("DROP TABLE " + first.getName());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch("DROP TABLE " + table.getName());
        }
        query.executeBatchAsync(callback);
    }

    public void dropTables(Table... tables) throws SQLException {
        dropTables(null, tables);
    }

    public void dropTable(Table table, Callback<Integer, SQLException> callback) {
        PreparedQuery query = prepareStatement("DROP TABLE " + table.getName());
        query.executeUpdateAsync(callback);
    }

    public void dropTable(Table table) {
        dropTable(table, null);
    }


    public void emptyTables(Callback<int[], SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement("TRUNCATE TABLE " + first.getName());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch("DROP TABLE " + table.getName());
        }
        query.executeBatchAsync(callback);
    }

    public void emptyTables(Table... tables) throws SQLException {
        emptyTables(null, tables);
    }

    public void emptyTable(Table table, Callback<Integer, SQLException> callback) {
        PreparedQuery query = prepareStatement("TRUNCATE TABLE " + table.getName());
        query.executeUpdateAsync(callback);
    }

    public void emptyTable(Table table) {
        emptyTable(table, null);
    }

    public PreparedQuery prepareStatement(String query) {
        return new PreparedQuery(connection, query);
    }
}
