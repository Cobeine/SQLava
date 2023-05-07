package me.cobeine.sqllava.table.column;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class Column {
    private final @Getter
    @NotNull String name;
    private final @NotNull ColumnType columnType;
    private int size;
    private @NotNull List<ColumnSettings> settings;
    private String defaultValue = null;


    private Column(@NotNull String name, @NotNull ColumnType type) {
        this.name = name;
        this.columnType = type;
        this.size = 0;
        this.settings = new ArrayList<>();
    }

    public static Column of(@NotNull String name, @NotNull ColumnType type) {
        return new Column(name, type);
    }

    public Column size(int size) {
        this.size = size;
        return this;
    }

    public Column defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Column settings(ColumnSettings... settings) {
        this.settings = List.of(settings);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('`').append(getName()).append("` ").append(columnType.name());
        if (size != 0) {
            builder.append("(").append(size).append(")");
        }
        if (!settings.isEmpty()) {
            builder.append(" ");
            for (ColumnSettings setting : settings) {
                builder.append(setting.getValue()).append(" ");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        if (defaultValue != null && !settings.contains(ColumnSettings.AUTO_INCREMENT)) {
            builder.append(" DEFAULT '").append(defaultValue).append("'");
        }
        return builder.toString();
    }

}
