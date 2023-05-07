package me.cobeine.sqllava.table.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */
@AllArgsConstructor
public enum ColumnSettings {

    AUTO_INCREMENT("AUTO_INCREMENT"),
    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE "),
    ZEROFILL("ZEROFILL"),
    UNSIGNED("UNSIGNED");

    private final @Getter String value;


}
