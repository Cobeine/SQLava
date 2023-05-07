package me.cobeine.sqllava.table.column;

import jdk.jfr.Unsigned;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public enum ColumnType {

    //A FIXED length string (can contain letters, numbers, and special characters). The size parameter specifies the column length in characters - can be from 0 to 255. Default is 1
    CHAR,
    //A VARIABLE length string (can contain letters, numbers, and special characters). The size parameter specifies the maximum column length in characters - can be from 0 to 65535
    VARCHAR,
    //	Equal to CHAR(), but stores binary byte strings. The size parameter specifies the column length in bytes. Default is 1
    BINARY,
    //	Equal to VARCHAR(), but stores binary byte strings. The size parameter specifies the maximum column length in bytes.
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
    //For BLOBs (Binary Large Objects). Holds up to 16,777,215 bytes of data
    MEDIUMBLOB,
    //Holds a string with a maximum length of 4,294,967,295 characters
    LONGTEXT,
    //For BLOBs (Binary Large Objects). Holds up to 4,294,967,295 bytes of data
    LONGBLOB,
    BIT,//A bit-value type. The number of bits per value is specified in size. The size parameter can hold a value from 1 to 64. The default value for size is 1.
    TINYINT,//A very small integer. Signed range is from -128 to 127. Unsigned range is from 0 to 255. The size parameter specifies the maximum display width (which is 255)
    BOOLEAN,//Zero is considered as false, nonzero values are considered as true.
    SMALLINT,//A small integer. Signed range is from -32768 to 32767. Unsigned range is from 0 to 65535. The size parameter specifies the maximum display width (which is 255)
    MEDIUMINT,//A medium integer. Signed range is from -8388608 to 8388607. Unsigned range is from 0 to 16777215. The size parameter specifies the maximum display width (which is 255)
    INT,//A medium integer. Signed range is from -2147483648 to 2147483647. Unsigned range is from 0 to 4294967295. The size parameter specifies the maximum display width (which is 255)
    BIGINT,//A large integer. Signed range is from -9223372036854775808 to 9223372036854775807. Unsigned range is from 0 to 18446744073709551615. The size parameter specifies the maximum display width (which is 255)
    FLOAT,//A floating point number. MySQL uses the p value to determine whether to use FLOAT or DOUBLE for the resulting data type. If p is from 0 to 24, the data type becomes FLOAT(). If p is from 25 to 53, the data type becomes DOUBLE()
    DOUBLE,//A normal-size floating point number. The total number of digits is specified in size. The number of digits after the decimal point is specified in the d parameter
    DECIMAL,//An exact fixed-point number. The total number of digits is specified in size. The number of digits after the decimal point is specified in the d parameter. The maximum number for size is 65. The maximum number for d is 30. The default value for size is 10. The default value for d is 0.
    DATE,//A date. Format: YYYY-MM-DD. The supported range is from '1000-01-01' to '9999-12-31'
    DATETIME,//A date and time combination. Format: YYYY-MM-DD hh:mm:ss. The supported range is from '1000-01-01 00:00:00' to '9999-12-31 23:59:59'. Adding DEFAULT and ON UPDATE in the column definition to get automatic initialization and updating to the current date and time
    TIMESTAMP,//A timestamp. TIMESTAMP values are stored as the number of seconds since the Unix epoch ('1970-01-01 00:00:00' UTC). Format: YYYY-MM-DD hh:mm:ss. The supported range is from '1970-01-01 00:00:01' UTC to '2038-01-09 03:14:07' UTC. Automatic initialization and updating to the current date and time can be specified using DEFAULT CURRENT_TIMESTAMP and ON UPDATE CURRENT_TIMESTAMP in the column definition
    TIME,//A time. Format: hh:mm:ss. The supported range is from '-838:59:59' to '838:59:59'
    YEAR;//A year in four-digit format. Values allowed in four-digit format: 1901 to 2155, and 0000.


}