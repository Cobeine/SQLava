package me.cobeine.sqlava.connection.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.cobeine.sqlava.connection.Callback;
import me.cobeine.sqlava.connection.auth.AuthenticatedConnection;
import me.cobeine.sqlava.connection.auth.BasicMySQLCredentials;
import me.cobeine.sqlava.connection.auth.CredentialsKey;
import me.cobeine.sqlava.connection.auth.CredentialsHolder;
import me.cobeine.sqlava.connection.database.query.PreparedQuery;
import me.cobeine.sqlava.connection.database.query.Query;
import me.cobeine.sqlava.connection.database.table.TableCommands;
import me.cobeine.sqlava.connection.pool.ConnectionPool;
import me.cobeine.sqlava.connection.pool.PooledConnection;
import me.cobeine.sqlava.connection.ConnectionResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@Getter
public class MySQLConnection implements AuthenticatedConnection<HikariDataSource>, PooledConnection<HikariDataSource, Connection>{

    private ConnectionPool<HikariDataSource,Connection> pool;
    private final CredentialsHolder credentialsHolder;
    private final Logger logger;
    private HikariDataSource dataSource;
    private final TableCommands TableCommands;

    public MySQLConnection(CredentialsHolder record) {
        this.credentialsHolder = record;
        this.TableCommands = new TableCommands(this);
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void connect(Callback<Integer, Exception> callback) {
        try {
            connect();
            callback.call(0,null);
        }catch (Exception e){
            callback.call(-1,e);
        }
    }
    @Override
    public ConnectionResult connect() {
        HikariConfig config = new HikariConfig();
        if (credentialsHolder.getProperty(BasicMySQLCredentials.DATASOURCE_CLASS_NAME,String.class) != null) {
            config.setDataSourceClassName(credentialsHolder.getProperty(BasicMySQLCredentials.DATASOURCE_CLASS_NAME, String.class));
        }
        if (credentialsHolder.getProperty(BasicMySQLCredentials.DRIVER, String.class) != null) {
            config.setDriverClassName(credentialsHolder.getProperty(BasicMySQLCredentials.DRIVER, String.class));
        }
        if (credentialsHolder.getProperty(BasicMySQLCredentials.JDBC_URL,String.class) != null) {
            config.setJdbcUrl(credentialsHolder.getProperty(BasicMySQLCredentials.JDBC_URL,String.class));
        }
        if (credentialsHolder.getProperty(BasicMySQLCredentials.MAX_LIFETIME,Long.class) != null) {
            config.setMaxLifetime(credentialsHolder.getProperty(BasicMySQLCredentials.MAX_LIFETIME,Long.class));
        }
        if (credentialsHolder.getProperty(BasicMySQLCredentials.POOL_SIZE,Integer.class) != null) {
            config.setMaximumPoolSize(credentialsHolder.getProperty(BasicMySQLCredentials.POOL_SIZE, Integer.class));
        }
        for (CredentialsKey credentialsKey : credentialsHolder.keySet()) {
            if (credentialsKey.isProperty()) {
                config.addDataSourceProperty(credentialsKey.getKey(), credentialsHolder.getProperty(credentialsKey,credentialsKey.getDataType()));
            }
        }

        dataSource = new HikariDataSource(config);
        this.pool = new ConnectionPool<>(dataSource) {
            @Override
            public Connection resource() {
                try {
                    return getSource().getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        try (Connection autoClosable = getPool().resource()) {
            return ConnectionResult.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConnectionResult.FAIL;
        }
    }

    public PreparedQuery prepareStatement(Query query) {
        return query.prepareStatement(this);
    }
    public PreparedQuery prepareStatement(String query) {
        return new PreparedQuery(this, query);
    }

    @Override
    public HikariDataSource getConnection() {
        return dataSource;
    }

}
