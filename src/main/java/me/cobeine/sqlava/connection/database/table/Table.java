package me.cobeine.sqlava.connection.database.table;

import lombok.Getter;
import me.cobeine.sqlava.connection.database.table.column.Column;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public abstract class Table {

    @Getter private final String name;
    private final List<Column> columns;
    private List<String> primaryKeys = new ArrayList<>();
    private final List<ForeignKey> foreignKeys;
    private final HashMap<String, String[]> uniqueKeys;

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

    public void setPrimaryKey(String... keys) {
        primaryKeys = Arrays.asList(keys);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + name + "` (");

        this.addColumns(builder);
        this.addPrimaryKeys(builder);
        this.addForeignKeys(builder);
        this.addUniqueKeys(builder);

        builder.append(")");
        return builder.toString();
    }

    private void addColumns(StringBuilder builder) {
        for (Column column : columns) {
            builder.append(column.toString()).append(", ");
        }
    }

    private void addPrimaryKeys(StringBuilder builder) {
        if (primaryKeys.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
            builder.deleteCharAt(builder.length() - 1); //to remove the last ", "
            return;
        }

        builder.append("PRIMARY KEY (");

        int size = primaryKeys.size();
        for (int i = 0; i < size; i++) {
            builder.append("`").append(primaryKeys.get(i)).append("`");
            if (i >= size - 1) break;

            builder.append(", ");
        }

        builder.append(")");
    }

    private void addForeignKeys(StringBuilder builder) {
        if (foreignKeys.isEmpty()) {
            return;
        }

        for (ForeignKey entry : foreignKeys) {
            if (entry.referencedColumn == null) continue;
            builder.append(", FOREIGN KEY (`").append(entry.foreignKey).append("`)");
            builder.append(" REFERENCES `").append(entry.referencedTable).append("`(`").append(entry.referencedColumn).append("`)");
            if (entry.onDelete == null) continue;

            builder.append(" ON DELETE ").append(entry.onDelete.name().replace("_"," "));
        }
    }

    private void addUniqueKeys(StringBuilder builder) {
        if (uniqueKeys.isEmpty()) {
            return;
        }

        for (String key : uniqueKeys.keySet()) {
            String[] columns = uniqueKeys.get(key);
            builder.append(", UNIQUE KEY `").append(key).append("` (");
            for (int i = 0; i < columns.length; i++) {
                builder.append("`").append(columns[i]).append("`");
                if (i >= columns.length - 1) continue;
                builder.append(", ");
            }
            builder.append(")");
        }
    }
}
