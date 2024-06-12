package me.cobeine.sqlava.connection.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@AllArgsConstructor
@Getter
public enum BasicMySQLCredentials implements CredentialsKey {
    HOST("serverName", String.class,true),
    PORT("port", Integer.class,true),
    DATABASE("databaseName", String.class,true),
    USERNAME("user", String.class,true),
    PASSWORD("password", String.class,true),
    MAX_LIFETIME("maximumLifetime", Integer.class,true),
    DATASOURCE_CLASS_NAME("dataSourceClassName", String.class,false),
    DRIVER("JdbcDriver", String.class,false),
    POOL_SIZE("poolSize", Integer.class,false),
    JDBC_URL("jdbcUrl",String.class,false);

    private final String key;
    private final Class<?> dataType;
    private final boolean property;

    @Override
    public CredentialsKey[] array() {
        return values();
    }
}
