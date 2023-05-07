package me.cobeine.sqllava.connection;

import com.zaxxer.hikari.HikariConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.cobeine.sqllava.utils.Credentials;

import java.util.Properties;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@AllArgsConstructor
@Getter
public enum ConnectionSource {

    HIKARI_DATASOURCE("com.mysql.jdbc.jdbc2.optional.MysqlDataSource",credentials -> {
        Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        HikariConfig config = new HikariConfig();
        Properties properties = new Properties();
        properties.put("serverName", credentials.getHost());
        properties.put("port", credentials.getPort());
        properties.put("databaseName", credentials.getDatabase());
        properties.put("user", credentials.getUsername());
        properties.put("password", credentials.getPassword());

        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.setDataSourceProperties(properties);
        config.setLeakDetectionThreshold(2000);
        config.setConnectionTimeout(8000);
        config.setMaximumPoolSize(16);
        return config;
    }),
    OLD_JDBC("com.mysql.jdbc.Driver",credentials -> jdbc("com.mysql.jdbc.Driver", credentials)),
    NEW_JDBC("com.mysql.cj.jdbc.Driver", credentials -> jdbc("com.mysql.cj.jdbc.Driver", credentials));


    private static HikariConfig jdbc(String class_name, Credentials credentials) throws ClassNotFoundException {
        Class.forName(class_name);

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(class_name);


        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());
        config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", credentials.getHost(), credentials.getPort(), credentials.getDatabase()) + "?autoReconnect=true");

        config.setLeakDetectionThreshold(2000);
        config.setConnectionTimeout(8000);
        config.setMaximumPoolSize(16);
        return config;
    }


    private final String className;
    private final SourceBuilder builder;

    public interface SourceBuilder{
      HikariConfig build(Credentials credentials) throws ClassNotFoundException;
  }
}
