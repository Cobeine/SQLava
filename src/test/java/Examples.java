import me.cobeine.sqlava.connection.auth.BasicMySQLCredentials;
import me.cobeine.sqlava.connection.auth.CredentialsHolder;
import me.cobeine.sqlava.connection.database.MySQLConnection;
import me.cobeine.sqlava.connection.database.query.PreparedQuery;
import me.cobeine.sqlava.connection.database.query.Query;
import me.cobeine.sqlava.connection.util.JdbcUrlBuilder;


/**
 * The type Examples.
 *
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */
public class Examples {
    private MySQLConnection connection;
    public Examples()throws Exception {
        var url = JdbcUrlBuilder.newBuilder()
                .host("host")
                .port(3306)
                .setAuto_reconnect(true)
                .database("database")
                .build();
        CredentialsHolder mysql_creds = CredentialsHolder.builder()
                .add(BasicMySQLCredentials.USERNAME,"username")
                .add(BasicMySQLCredentials.PASSWORD,"password")
                .add(BasicMySQLCredentials.DATABASE,"database")
                .add(BasicMySQLCredentials.PORT,3306)
                .add(BasicMySQLCredentials.POOL_SIZE,8)
                .add(BasicMySQLCredentials.JDBC_URL, url)
                .build();

        connection = new MySQLConnection(mysql_creds);
        connection.connect(result -> {
            if (result.getException().isEmpty()) {
                result.getException().get().printStackTrace();
                return;
            }
            //successfully connected
            connection.getTableCommands().createTable(new ExampleTable());
            connection.getTableCommands().createTable(new ExampleTable(),tableResult -> {
                if (tableResult.getException().isPresent()) {
                    tableResult.getException().get().printStackTrace();
                    return;
                }
                //successfully created table
            });
        });

        PreparedQuery query = connection.prepareStatement("SELECT * FROM example WHERE uuid=test");
        query = connection.prepareStatement(Query.select("example").where("uuid", "test"));

        query.executeQuery();
        query.executeQueryAsync(result -> {
            if (result.getException().isPresent()) {
                result.getException().get().printStackTrace();
                return;
            }
            //successfully executed query
            result.getResult().ifPresent(resultSet -> {
                //...
            });
        });

        Query update = Query.update("example").set("uuid","test").where("id",1).and("uuid","test2");

        update = Query.update("example").set("uuid").where("id").where("uuid");
        connection.prepareStatement(update)
                .setParameter(1,"test")
                .setParameter(2,1)
                .setParameter(3,"test2").executeUpdateAsync(result -> {

                    if (result.getException().isPresent()) {
                        result.getException().get().printStackTrace();
                        return;
                    }
                    //successfully executed query
                });
    }


}
