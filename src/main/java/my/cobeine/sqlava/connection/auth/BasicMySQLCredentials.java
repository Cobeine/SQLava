package my.cobeine.sqlava.connection.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@AllArgsConstructor
@Getter
public enum BasicMySQLCredentials implements CredentialsKey {
    HOST("serverName", String.class, true,true),
    PORT("port", Integer.class, true,true),
    DATABASE("databaseName", String.class, true,true),
    USERNAME("user", String.class, true,true),
    PASSWORD("password", String.class, true,true),
    MAX_LIFETIME("maximumLifetime", Integer.class, false,true),
    DATASOURCE_CLASS_NAME("dataSourceClassName", String.class, false,false),
    POOL_SIZE("poolSize", Integer.class, false,false);

    private final String key;
    private final Class<?> dataType;
    private final boolean required, property;

    @Override
    public CredentialsKey[] array() {
        return values();
    }
}
