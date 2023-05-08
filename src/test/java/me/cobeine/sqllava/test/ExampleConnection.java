package me.cobeine.sqllava.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.cobeine.sqllava.connection.Callback;
import me.cobeine.sqllava.connection.ConnectionResult;
import me.cobeine.sqllava.connection.ConnectionSource;
import me.cobeine.sqllava.connection.SQLConnection;
import me.cobeine.sqllava.utils.Credentials;

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
                    onResult(ConnectionResult.FAIL);
                    return;
                }
                onResult(ConnectionResult.SUCCESS);
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection(Callback<Integer, Throwable> result) throws ClassNotFoundException {
        disableLogging();

        HikariConfig config = ConnectionSource.HIKARI_DATASOURCE.getBuilder().apply(credentials);

        this.dataSource = new HikariDataSource(config);
        result.call(0, null);
    }

    @Override
    public void onResult(ConnectionResult result) {
        if (result.equals(ConnectionResult.SUCCESS))
            createTable(new ExampleTable()); //dropTable(table) too

    }


}
