package me.cobeine.sqlava.connection.auth;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class CredentialsRecord {
    private final Map<CredentialsKey,Object> record;

    public CredentialsRecord() {
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

    public static CredentialsRecordBuilder builder() {
        return new CredentialsRecordBuilder();
    }

    public List<CredentialsKey> keySet() {
        return new ArrayList<>(record.keySet());
    }

    public static class CredentialsRecordBuilder {

        private final CredentialsRecord recorder;

        public CredentialsRecordBuilder() {
            this.recorder = new CredentialsRecord();
        }

        public CredentialsRecordBuilder add(CredentialsKey key, Object value) {
            recorder.record.put(key, value);
            return this;
        }
        public CredentialsRecord build() {
            return recorder;
        }
    }


}
