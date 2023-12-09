package me.cobeine.sqlava.connection.presets;

import me.cobeine.sqlava.connection.auth.BasicMySQLCredentials;
import me.cobeine.sqlava.connection.auth.CredentialsRecord;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface HikariDataSourcePresets {

    CredentialsRecord DEFAULT = CredentialsRecord.builder()
            .add(BasicMySQLCredentials.DATASOURCE_CLASS_NAME, "com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
            .build();

    CredentialsRecord OLD_JDBC = CredentialsRecord.builder()
            .add(BasicMySQLCredentials.DATASOURCE_CLASS_NAME, "com.mysql.jdbc.Driver")
            .build();

    CredentialsRecord NEW_JDBC = CredentialsRecord.builder()
            .add(BasicMySQLCredentials.DATASOURCE_CLASS_NAME,"com.mysql.cj.jdbc.Driver")
            .build();

}
