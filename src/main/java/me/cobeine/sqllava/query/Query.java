package me.cobeine.sqllava.query;

import me.cobeine.sqllava.connection.SQLConnection;
import me.cobeine.sqllava.query.impl.DeleteQuery;
import me.cobeine.sqllava.query.impl.InsertQuery;
import me.cobeine.sqllava.query.impl.SelectQuery;
import me.cobeine.sqllava.query.impl.UpdateQuery;

import java.util.Collection;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public interface Query {

    default PreparedQuery prepareStatement(SQLConnection sqlConnection) {
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


    static SelectQuery SELECT(String from) {
        return new SelectQuery(from);
    }
    static DeleteQuery DELETE(String from) {
        return new DeleteQuery(from);
    }
    static InsertQuery INSERT(String into) {
        return new InsertQuery(into);
    }
    static UpdateQuery UPDATE(String into) {
        return new UpdateQuery(into);
    }
}
