package me.cobeine.sqllava.table.column;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public enum ColumnType {

    // (size)	A FIXED length string (can contain letters, numbers, and special characters). The size parameter specifies the column length in characters - can be from 0 to 255. Default is 1
    CHAR,
    //(size),	A VARIABLE length string (can contain letters, numbers, and special characters). The size parameter specifies the maximum column length in characters - can be from 0 to 65535
    VARCHAR,
    //(size)	Equal to CHAR(), but stores binary byte strings. The size parameter specifies the column length in bytes. Default is 1
    BINARY,
    //(size)	Equal to VARCHAR(), but stores binary byte strings. The size parameter specifies the maximum column length in bytes.
    VARBINARY,
    //	For BLOBs (Binary Large Objects). Max length: 255 bytes
    TINYBLOB,
    //	Holds a string with a maximum length of 255 characters
    TINYTEXT,
    //Holds a string with a maximum length of 65,535 bytes
    TEXT,
    //For BLOBs (Binary Large Objects). Holds up to 65,535 bytes of data
    BLOB,
    //Holds a string with a maximum length of 16,777,215 characters
    MEDIUMTEXT,
    //	For BLOBs (Binary Large Objects). Holds up to 16,777,215 bytes of data
    MEDIUMBLOB,
    //Holds a string with a maximum length of 4,294,967,295 characters
    LONGTEXT,
    //For BLOBs (Binary Large Objects). Holds up to 4,294,967,295 bytes of data
    LONGBLOB,
    //  ints
    INT;
}