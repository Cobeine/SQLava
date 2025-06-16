package me.cobeine.sqlava.connection.auth;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface CredentialsKey {


    String getKey();

    boolean isProperty();

    CredentialsKey[] array();

    Class<?> getDataType();
    static CredentialsKey of(String key, Class<?> dataType, boolean property) {
        return new CredentialsKey() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public boolean isProperty() {
                return property;
            }

            @Override
            public CredentialsKey[] array() {
                return new CredentialsKey[]{this};
            }

            @Override
            public Class<?> getDataType() {
                return dataType;
            }
        };
    }
}
