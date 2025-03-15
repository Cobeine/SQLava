package me.cobeine.sqlava.connection.database.table;

import lombok.Getter;
import me.cobeine.sqlava.connection.database.table.column.Column;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public abstract class Table {

    @Getter private final String name;
    private final List<Column> columns;
    private String primaryKey;
    private final List<ForeignKey> foreignKeys;
    private final HashMap<String,String[]> uniqueKeys;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.foreignKeys = new ArrayList<>();
        this.uniqueKeys = new HashMap<>();
    }

    public void addColumn(@NotNull Column column) {
        this.columns.add(column);
    }

    public ForeignKey foreignKey(String key) {
        ForeignKey entry = new ForeignKey(key);
        foreignKeys.add(entry);
        return entry;
    }
    public void uniqueKey(String key, String... columns) {
        uniqueKeys.put(key, columns);
    }

    public void addColumns(@NotNull Column... columns) {
        for (Column column : columns) {
            addColumn(column);
        }
    }

    public void setPrimaryKey(@NotNull Column primaryKey) {
        this.primaryKey = primaryKey.getName();
    }
    public void setPrimaryKey(@NotNull String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS `").append(name).append("` (");
        for (Column column : columns) {
            builder.append(column.toString()).append(", ");
        }
        if (primaryKey != null) {
            builder.append("PRIMARY KEY (`").append(primaryKey).append("`)");
        } else {
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1); //to remove the last ", "
        }
        if (!foreignKeys.isEmpty()) {
            for (ForeignKey entry : foreignKeys) {
                if (entry.referencedColumn != null) {
                    builder.append(", FOREIGN KEY (`").append(entry.foreignKey).append("`)");
                    builder.append(" REFERENCES `").append(entry.referencedTable).append("`(`").append(entry.referencedColumn).append("`)");
                    if (entry.onDelete != null) {
                        builder.append(" ON DELETE ").append(entry.onDelete.name().replace("_"," "));
                    }
                }
            }
        }
        if (!uniqueKeys.isEmpty()) {
            for (String key : uniqueKeys.keySet()) {
                String[] columns = uniqueKeys.get(key);
                builder.append(", UNIQUE KEY `").append(key).append("` (");
                for (int i = 0; i < columns.length; i++) {
                    builder.append("`").append(columns[i]).append("`");
                    if (i < columns.length - 1) {
                        builder.append(", ");
                    }
                }
                builder.append(")");
            }
        }
        builder.append(")");
        return builder.toString();
    }


}