package my.cobeine.sqlava.connection.auth;


import java.util.HashMap;
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
            throw new IllegalStateException("Invalid Key: " + key);
        }
        return dataType.cast(record.get(key));
    }

    public static CredentialsRecordBuilder builder() {
        return new CredentialsRecordBuilder();
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
