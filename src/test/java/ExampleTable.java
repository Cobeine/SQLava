import me.cobeine.sqlava.connection.database.table.Table;
import me.cobeine.sqlava.connection.database.table.column.Column;
import me.cobeine.sqlava.connection.database.table.column.ColumnSettings;
import me.cobeine.sqlava.connection.database.table.column.ColumnType;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class ExampleTable extends Table {

    public ExampleTable() {
        super("example");
        addColumns(
                Column.of("id", ColumnType.INT).settings(ColumnSettings.AUTO_INCREMENT,ColumnSettings.UNIQUE),
                Column.of("uuid", ColumnType.TEXT).settings(ColumnSettings.NOT_NULL,ColumnSettings.UNIQUE).defaultValue("none")
        );
        setPrimaryKey("id");
    }
}
