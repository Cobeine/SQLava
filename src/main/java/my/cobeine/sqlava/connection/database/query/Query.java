package my.cobeine.sqlava.connection.database.query;

import my.cobeine.sqlava.connection.database.MySQLConnection;
import my.cobeine.sqlava.connection.database.query.impl.SelectQuery;
import my.cobeine.sqlava.connection.database.query.impl.DeleteQuery;
import my.cobeine.sqlava.connection.database.query.impl.InsertQuery;
import my.cobeine.sqlava.connection.database.query.impl.UpdateQuery;

import java.util.Collection;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Query {

    default PreparedQuery prepareStatement(MySQLConnection sqlConnection) {
        return new PreparedQuery(sqlConnection, build());
    }

    String build();

    default String separate(Collection<String> collection, String separator) {
        StringBuilder builder = new StringBuilder();
        String sep = "";
        for (String item : collection) {
            builder.append(sep).append(item);
            sep = separator;
        }
        return builder.toString();
    }
    default String separate(Collection<String> collection) {
        return separate(collection, ",");
    }


    static SelectQuery select(String from) {
        return new SelectQuery(from);
    }
    static DeleteQuery delete(String from) {
        return new DeleteQuery(from);
    }
    static InsertQuery insert(String into) {
        return new InsertQuery(into);
    }
    static UpdateQuery update(String into) {
        return new UpdateQuery(into);
    }
}
