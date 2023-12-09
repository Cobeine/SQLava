package my.cobeine.sqlava.connection.auth;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface CredentialsKey {


    String getKey();

    boolean isProperty();

    CredentialsKey[] array();

    Class<?> getDataType();
}
