package my.cobeine.sqlava.connection.auth;

import my.cobeine.sqlava.connection.Connection;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface AuthenticatedConnection<C> extends Connection<C> {

    CredentialsRecord getCredentialsRecord();
}
