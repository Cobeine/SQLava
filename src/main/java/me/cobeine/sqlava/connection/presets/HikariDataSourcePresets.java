package me.cobeine.sqlava.connection.presets;

import me.cobeine.sqlava.connection.auth.BasicMySQLCredentials;
import me.cobeine.sqlava.connection.auth.CredentialsRecord;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface HikariDataSourcePresets {

    String DATA_SOURCE = ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
    String OLD_JDBC = ("com.mysql.jdbc.Driver");
    String NEW_JDBC = ("com.mysql.cj.jdbc.Driver");



}
