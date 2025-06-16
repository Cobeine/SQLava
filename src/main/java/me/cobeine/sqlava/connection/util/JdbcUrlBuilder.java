package me.cobeine.sqlava.connection.util;

import java.util.HashMap;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public final class JdbcUrlBuilder {

    public static final String BASE_URL = "jdbc:mysql://%s:%s/%s";
    private boolean autoReconnect;
    private boolean autoCreateDatabase;
    private final HashMap<String, Object> map;

    JdbcUrlBuilder() {
        this.map = new HashMap<>();
    }

    public JdbcUrlBuilder host(String host) {
        map.put("host", host);
        return this;
    }

    public JdbcUrlBuilder port(int port) {
        map.put("port", port);
        return this;
    }
    public JdbcUrlBuilder autoCreateDatabase(boolean autoCreateDatabase) {
        this.autoCreateDatabase = autoCreateDatabase;
        return this;
    }

    public JdbcUrlBuilder database(String database) {
        map.put("database", database);
        return this;
    }

    public JdbcUrlBuilder setAutoReconnect(boolean auto_reconnect) {
        this.autoReconnect = auto_reconnect;
        return this;
    }

    public String build() {
        return String.format(BASE_URL, map.get("host"), map.get("port"), map.get("database")) + (autoReconnect ? "?autoReconnect=true" : "") + (autoCreateDatabase ? "&createDatabaseIfNotExist=true" : "");
    }

    public static JdbcUrlBuilder newBuilder() {
        return new JdbcUrlBuilder();
    }

}
