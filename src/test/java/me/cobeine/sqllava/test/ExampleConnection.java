package me.cobeine.sqllava.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.cobeine.sqllava.connection.Callback;
import me.cobeine.sqllava.connection.ConnectionResult;
import me.cobeine.sqllava.connection.SQLConnection;
import me.cobeine.sqllava.utils.Credentials;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@Getter
public class ExampleConnection implements SQLConnection {


    private final ExecutorService threadPool;
    private HikariDataSource dataSource;
    private final Credentials credentials;

    public ExampleConnection(int threads) {
        this.threadPool = Executors.newFixedThreadPool(threads);
        this.credentials = Credentials.defaultCredentials;
        try {
            openConnection((result, throwable) -> {
                if (throwable != null) {
                    throwable.printStackTrace();
                    onConnectionSuccess(ConnectionResult.FAIL);
                    return;
                }
                onConnectionSuccess(ConnectionResult.SUCCESS);
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection(Callback<Integer, Throwable> result) throws ClassNotFoundException {
            disableLogging();
            String dataSource = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
            Class.forName(dataSource);

            HikariConfig config = new HikariConfig();
            Properties properties = new Properties();
            properties.put("serverName", credentials.getHost());
            properties.put("port", credentials.getPort());
            properties.put("databaseName", credentials.getDatabase());
            properties.put("user", credentials.getUsername());
            properties.put("password", credentials.getPassword());

            config.setDataSourceClassName(dataSource);
            config.setDataSourceProperties(properties);
            config.setLeakDetectionThreshold(2000);
            config.setConnectionTimeout(8000);
            config.setMaximumPoolSize(16);
            this.dataSource = new HikariDataSource(config);
            result.call(0, null);
    }

    @Override
    public void onConnectionSuccess(ConnectionResult result) {
        if (result.equals(ConnectionResult.SUCCESS))
            createTable(new ExampleTable());
    }


}
