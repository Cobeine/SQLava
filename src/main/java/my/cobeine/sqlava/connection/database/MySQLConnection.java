package my.cobeine.sqlava.connection.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import my.cobeine.sqlava.connection.ConnectionResult;
import my.cobeine.sqlava.connection.auth.AuthenticatedConnection;
import my.cobeine.sqlava.connection.auth.BasicMySQLCredentials;
import my.cobeine.sqlava.connection.auth.CredentialsKey;
import my.cobeine.sqlava.connection.auth.CredentialsRecord;
import my.cobeine.sqlava.connection.pool.ConnectionPool;
import my.cobeine.sqlava.connection.pool.PooledConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@Getter
public class MySQLConnection implements AuthenticatedConnection<HikariDataSource>, PooledConnection<HikariDataSource,Connection> {

    private ConnectionPool<HikariDataSource,Connection> pool;
    private final CredentialsRecord credentialsRecord;
    private final Logger logger;
    private HikariDataSource dataSource;

    public MySQLConnection(CredentialsRecord record) {
        this.credentialsRecord = record;
        logger = Logger.getLogger(this.getClass().getName());
    }


    @Override
    public ConnectionResult connect() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(credentialsRecord.getProperty(BasicMySQLCredentials.DATASOURCE_CLASS_NAME, String.class));
        config.setMaximumPoolSize(credentialsRecord.getProperty(BasicMySQLCredentials.POOL_SIZE, Integer.class));

        for (CredentialsKey credentialsKey : BasicMySQLCredentials.values()) {
            if (credentialsKey.isProperty()) {
                config.addDataSourceProperty(credentialsKey.getKey(), credentialsRecord.getProperty(credentialsKey,credentialsKey.getDataType()));
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


    @Override
    public HikariDataSource getConnection() {
        return dataSource;
    }

}
