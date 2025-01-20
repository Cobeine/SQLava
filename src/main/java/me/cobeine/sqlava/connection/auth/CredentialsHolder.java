package me.cobeine.sqlava.connection.auth;


import java.util.*;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class CredentialsHolder {
    private final Map<CredentialsKey,Object> record;

    public CredentialsHolder() {
        record = new HashMap<>();
    }
    public <T> T getProperty(CredentialsKey key, Class<T> dataType) {
        if (!record.containsKey(key)) {
            if (key.equals(BasicMySQLCredentials.JDBC_URL)) {
                return null;
            }
            if (key.equals(BasicMySQLCredentials.MAX_LIFETIME)) {
                return null;
            }
            if (key.isProperty()) {
                throw new IllegalStateException("Key not found: " + key.getKey());
            }
        }
        return dataType.cast(record.get(key));
    }
    public <T> Optional<T> get(CredentialsKey key, Class<T> dataType) {
        if (!record.containsKey(key)) {
           return Optional.empty();
        }
        return Optional.of(dataType.cast(record.get(key)));
    }

    public static CredentialsRecordBuilder builder() {
        return new CredentialsRecordBuilder();
    }

    public List<CredentialsKey> keySet() {
        return new ArrayList<>(record.keySet());
    }

    public static class CredentialsRecordBuilder {

        private final CredentialsHolder recorder;

        public CredentialsRecordBuilder() {
            this.recorder = new CredentialsHolder();
        }

        public CredentialsRecordBuilder add(CredentialsKey key, Object value) {
            recorder.record.put(key, value);
            return this;
        }
        public CredentialsHolder build() {
            return recorder;
        }
    }


}
