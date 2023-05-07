package me.cobeine.sqllava.utils;

import lombok.Builder;
import lombok.Getter;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@Builder
@Getter
public class Credentials {

    private final String host, port, username, password, database;

    public static final Credentials defaultCredentials =
            Credentials.builder()
                    .host("localhost")
                    .username("root")
                    .password("")
                    .database("default")
                    .port("3306")
                    .build();

}
