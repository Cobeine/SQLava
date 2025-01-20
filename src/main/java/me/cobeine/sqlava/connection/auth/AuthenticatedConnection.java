package me.cobeine.sqlava.connection.auth;

import me.cobeine.sqlava.connection.Connection;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface AuthenticatedConnection<C> extends Connection<C> {

    CredentialsHolder getCredentialsHolder();
}
