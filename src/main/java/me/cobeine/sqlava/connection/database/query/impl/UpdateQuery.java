package me.cobeine.sqlava.connection.database.query.impl;

import me.cobeine.sqlava.connection.database.query.Query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class UpdateQuery implements Query {
    private final String table;
    private final LinkedHashMap<String, String> values = new LinkedHashMap<>();
    private final List<String> wheres = new ArrayList<>();

    public UpdateQuery(String table) {
        this.table = table;
    }

    public UpdateQuery setMultiple(String... column) {
        for (String s : column) {
            set(s, "?");
        }
        return this;
    }
    public UpdateQuery set(String column, String value) {
        values.put(column, value);
        return this;
    }

    public UpdateQuery set(String column) {
        set(column, "?");
        return this;
    }
    public UpdateQuery where(String expression) {
        wheres.add(expression + "=?");
        return this;
    }
    public UpdateQuery where(String... expression) {
        for (String s : expression) {
            wheres.add(s + "=?");
        }
        return this;
    }
    public UpdateQuery where(String expression,Object value) {
        wheres.add(expression + "=" + (value instanceof String ? "'" + value +"'" : value));
        return this;
    }
    public UpdateQuery and(String expression) {
        where(expression);
        return this;
    }
    public UpdateQuery and(String expression,Object value) {
        where(expression,value);
        return this;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(table).append(" SET ");

        String seperator = "";
        for (Map.Entry<String, String> entry : values.entrySet()) {

            String column = entry.getKey();
            String value = entry.getValue();
            builder.append(seperator)
                    .append(column)
                    .append("=")
                    .append(value);
            seperator = ",";

        }

        if (!wheres.isEmpty())
            builder.append(" WHERE ").append(separate(wheres, " AND "));

        return builder.toString();
    }
}
