package my.cobeine.sqlava.connection.database.query.impl;

import my.cobeine.sqlava.connection.database.query.Query;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class InsertQuery implements Query {
    private final String table;
    private final LinkedHashMap<String, String> values = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> duplicateValues = new LinkedHashMap<>();
    private boolean onDuplicateKey = false;

    public InsertQuery(String table) {
        this.table = table;
    }


    public InsertQuery value(String column, String value) {
        values.put(column, value);
        return this;
    }


    public InsertQuery value(String column) {
        value(column, "?");
        return this;
    }
    public InsertQuery values(String... column) {
        for (String s : column) {
            values.put(s, "?");
        }
        return this;
    }

    public InsertQuery onDuplicateKeyUpdate() {
        onDuplicateKey = true;
        return this;
    }


    public InsertQuery set(String column, String value) {
        duplicateValues.put(column, value);
        return this;
    }


    public InsertQuery set(String column) {
        set(column, "VALUES(" + column + ")");
        return this;
    }


    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(table).append(" (")
                .append(separate(values.keySet())).append(")").append(" VALUES (").append(separate(values.values())).append(")");

        if (onDuplicateKey) {
            builder.append(" ON DUPLICATE KEY UPDATE ");

            String separator = "";
            for (Map.Entry<String, String> entry : duplicateValues.entrySet()) {
                String column = entry.getKey();
                String value = entry.getValue();
                builder.append(separator).append(column).append("=").append(value);
                separator = ",";
            }

        }
        return builder.toString();
    }


}
