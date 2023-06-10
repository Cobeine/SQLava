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
                .setParameter(1, 5)
                .setParameter(2, UUID.randomUUID())
                .executeQueryAsync(result ->
                        result.executeIfPresent(resultSet -> {

                            int id = resultSet.getInt("id"); //examples

                        }).orElse(exception -> {

                            System.out.println(exception);

                        }).apply()
                );

        //or

        Query selectQuery = Query.select("table").where("uuid").and("id");
        PreparedQuery query = selectQuery.prepareStatement(connection);
        query.setParameter(1, UUID.randomUUID()).setParameter(2, 1);
        try (ResultSet set = query.executeQuery()) {
            set.getInt("id");//etc
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    void test(ExampleConnection connection) throws SQLException {
        Query select = Query.insert("table").values("id", "uuid", "kills", "deaths", "coins");
        Query update = Query.update("table").set("name").and("phone").where("id");
        connection.prepareStatement(update)
        .setParameter(1,"john cena")
        .setParameter(2,"3060692012")
        .setParameter(3,69)
        .executeUpdate();
        System.out.println(select.build());
    }

}
