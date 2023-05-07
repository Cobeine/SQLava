package me.cobeine.sqllava.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.java.Log;
import me.cobeine.sqllava.query.PreparedQuery;
import me.cobeine.sqllava.query.Query;
import me.cobeine.sqllava.table.Table;
import me.cobeine.sqllava.utils.Credentials;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface SQLConnection {


    void openConnection(Callback<Integer,Throwable> result) throws ClassNotFoundException;
    void onConnectionSuccess();

    ExecutorService getThreadPool();

    HikariDataSource getDataSource();

    Credentials getCredentials();

    default boolean isClosed() {
        return getDataSource() == null || getDataSource().isClosed();
    }
    default Connection getConnection() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    default void close() {
        if (isClosed())
            return;

        getDataSource().close();
        getThreadPool().shutdown();
    }

    default PreparedQuery prepareStatement(Query query) {
      return query.prepareStatement(this);
    }
    default PreparedQuery prepareStatement(String query) {
        return new PreparedQuery(this, query);
    }
    default void disableLogging() {
        Logger.getLogger("com.zaxxer.hikari.pool.PoolBase").setLevel(Level.OFF);
        Logger.getLogger("com.zaxxer.hikari.pool.HikariPool").setLevel(Level.OFF);
        Logger.getLogger("com.zaxxer.hikari.HikariDataSource").setLevel(Level.OFF);
        Logger.getLogger("com.zaxxer.hikari.HikariConfig").setLevel(Level.OFF);
        Logger.getLogger("com.zaxxer.hikari.util.DriverDataSource").setLevel(Level.OFF);
    }


    default Logger getLogger() {
        return Logger.getLogger("mysqlconn");
    }
    default void createTables(Callback<int[],SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement(first.toString());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch(table.toString());
        }
        query.executeBatchAsync(callback);
    }
    default void createTables(Table... tables) throws SQLException {
        createTables(null, tables);
    }
    default void createTable(Table table,Callback<Integer,SQLException> callback){
        PreparedQuery query = prepareStatement(table.toString());
        query.executeUpdateAsync(callback);
    }
    default void createTable(Table table){
        createTable(table, null);
    }

    default void dropTables(Callback<int[],SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement("DROP TABLE " + first.getName());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch("DROP TABLE " + table.getName());
        }
        query.executeBatchAsync(callback);
    }
    default void dropTables(Table... tables) throws SQLException {
        dropTables(null, tables);
    }
    default void dropTable(Table table,Callback<Integer,SQLException> callback){
        PreparedQuery query = prepareStatement("DROP TABLE "+ table.getName());
        query.executeUpdateAsync(callback);
    }
    default void dropTable(Table table){
        dropTable(table, null);
    }


    default void emptyTables(Callback<int[],SQLException> callback, Table... tables) throws SQLException {
        Table first = tables[0];
        PreparedQuery query = prepareStatement("TRUNCATE TABLE " + first.getName());
        for (Table table : tables) {
            if (table.equals(first)) continue;
            query.addBatch("DROP TABLE " + table.getName());
        }
        query.executeBatchAsync(callback);
    }
    default void emptyTables(Table... tables) throws SQLException {
        emptyTables(null, tables);
    }
    default void emptyTable(Table table,Callback<Integer,SQLException> callback){
        PreparedQuery query = prepareStatement("TRUNCATE TABLE "+ table.getName());
        query.executeUpdateAsync(callback);
    }
    default void emptyTable(Table table){
        emptyTable(table, null);
    }
}
