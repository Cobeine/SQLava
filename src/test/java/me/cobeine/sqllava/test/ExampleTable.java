package me.cobeine.sqllava.test;

import me.cobeine.sqllava.table.Table;
import me.cobeine.sqllava.table.column.Column;
import me.cobeine.sqllava.table.column.ColumnSettings;
import me.cobeine.sqllava.table.column.ColumnType;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class ExampleTable extends Table {


    public ExampleTable() {
        super("example");
        addColumns(
                Column.of("id", ColumnType.INT).settings(ColumnSettings.AUTO_INCREMENT, ColumnSettings.UNIQUE),
                Column.of("uuid", ColumnType.TEXT).settings(ColumnSettings.NOT_NULL, ColumnSettings.UNIQUE).defaultValue("none"),
                Column.of("name", ColumnType.VARCHAR).size(64).settings(ColumnSettings.NOT_NULL, ColumnSettings.UNIQUE).defaultValue("none"),
                Column.of("rank", ColumnType.VARCHAR).size(64).settings(ColumnSettings.NOT_NULL).defaultValue("DEFAULT"),
        );
        setPrimaryKey("id");
    }
}
