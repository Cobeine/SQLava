package me.cobeine.sqllava.test;

import me.cobeine.sqllava.query.PreparedQuery;
import me.cobeine.sqllava.query.Query;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public class ExampleQueries {

    public void examples(ExampleConnection connection) {

        connection.prepareStatement(
                Query.select("test").where("id").and("uuid"))
                .setParameter(1,5)
                .setParameter(2, UUID.randomUUID())
                .executeQueryAsync((result, throwable) -> {

                });

        //or

        Query selectQuery = Query.select("table").where("uuid").and("id");
        PreparedQuery query = selectQuery.prepareStatement(connection);
        query.setParameter(1, UUID.randomUUID()).setParameter(2, 1);
        try( ResultSet set = query.executeQuery()) {
            set.getInt("id");//etc
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    void test() {
        Query select = Query.insert("table").values("id","uuid","kills","deaths","coins");
        System.out.println(select.build());
    }

}
