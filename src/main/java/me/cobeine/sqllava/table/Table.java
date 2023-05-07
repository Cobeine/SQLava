package me.cobeine.sqllava.table;

import lombok.Getter;
import me.cobeine.sqllava.table.column.Column;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public abstract class Table {

    @Getter String name;
    List<Column> columns;
    String primaryKey;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    public void addColumn(@NotNull Column column) {
        this.columns.add(column);
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
        builder.append(")");
        return builder.toString();
    }


}