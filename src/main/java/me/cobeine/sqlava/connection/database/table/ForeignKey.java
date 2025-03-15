package me.cobeine.sqlava.connection.database.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@RequiredArgsConstructor
public class ForeignKey {
    final String foreignKey;
    String referencedColumn;
    String referencedTable;
    OnDelete onDelete;


    public static ForeignKey foreignKey(String foreignKey) {
        return new ForeignKey(foreignKey);
    }

    public ForeignKey references(String referencedTable, String referencedColumn) {
        this.referencedTable = referencedTable;
        this.referencedColumn = referencedColumn;
        return this;
    }

    public ForeignKey onDelete(OnDelete onDelete) {
        this.onDelete = onDelete;
        return this;
    }
}
