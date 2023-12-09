<img src="https://cdn.discordapp.com/attachments/1070339329420951694/1182980064183328828/sql_lava_logo.png" alt="sqlava logo">  

<p align="center" href="https://www.codefactor.io/repository/github/cobeine/sqlava"><img src="https://www.codefactor.io/repository/github/cobeine/sqlava/badge" alt="CodeFactor" /></p> 
  
# <p align="center">SQLava</p>
 <p align="center">A simple & easy to use Java MySQL Query Builder</p>
I've made this project a while ago in my free time while having exams & I've decided to publish it to everyone!
The purpose of this plugin is to make the process of creating a mysql connection & creating quries fun and easy!

## Installation
[![](https://jitpack.io/v/Cobeine/SQLava.svg)](https://jitpack.io/#Cobeine/SQLava)
<p></p>
If you're using maven or gradle as a dependency manager then just follow the steps below.
Otherwise, just download the jar and add it as an artifact dependency.

### Adding the repository

###### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

###### Gradle
```groovy

repositories {
    mavenCentral()
    maven {
        url 'https://jitpack.io'
    }
}
```

### Implementing the dependency

###### Maven
```xml
<dependency>
    <groupId>com.github.Cobeine</groupId>
    <artifactId>SQLava</artifactId>
    <version>1.5.0-SNAPSHOT</version>
</dependency>
```

###### Gradle
```groovy

dependencies {
	   implementation 'com.github.Cobeine:SQLava:1.5.0-SNAPSHOT'
    //Please note that my library is dependant on Hikrai
    //So, make sure to implement HikariCP in your project.
}
```

### Examples

#### Connection Examples:
```java
public class Test {
    /**
     * Connection example
     * for driver classes @see me.cobeine.sqlava.connection.HikariDataSourcePresets.class
     */
    private MySQLConnection connection = new MySQLConnection(CredentialsRecord.builder()
            .add(BasicMySQLCredentials.DATABASE, "test")
            .add(BasicMySQLCredentials.HOST, "host")
            .add(BasicMySQLCredentials.PASSWORD, "password")
            .add(BasicMySQLCredentials.JDBC_URL, "url") //use JdbcUrlBuilder
            .build());
    
    void methods(){
        connection.connect();
        connection.getLogger();
        connection.getPool();
        connection.getCredentialsRecord();
        connection.closeConnection();
        connection.prepareStatement(); // with args;

        JdbcUrlBuilder.newBuilder()
                .host("host")
                .port(3306)
                .database("test")
                .setAuto_reconnect(true)
                .build();
    }
    
}

```
#### Table Example
```java 

public class ExampleTable extends Table {


    public ExampleTable() {
        super("example");
        addColumns(
                Column.of("id", ColumnType.INT).settings(ColumnSettings.AUTO_INCREMENT, ColumnSettings.UNIQUE),
                Column.of("uuid", ColumnType.TEXT).settings(ColumnSettings.NOT_NULL, ColumnSettings.UNIQUE).defaultValue("none"),
                Column.of("name", ColumnType.VARCHAR).size(64).settings(ColumnSettings.NOT_NULL, ColumnSettings.UNIQUE).defaultValue("none"),
                Column.of("rank", ColumnType.VARCHAR).size(64).settings(ColumnSettings.NOT_NULL).defaultValue("DEFAULT")
                );
        setPrimaryKey("id");

    }
}
```

#### Query examples
```java
public class Examples {
    public void examples() {

        connection.prepareStatement(
                        Query.select("test").where("id").and("uuid"))
                .setParameter(1, 5)
                .setParameter(2, UUID.randomUUID())
                .executeQueryAsync(result ->
                        result.executeIfPresent(resultSet -> {

                            int id = resultSet.getInt("id"); //examples

                        }).orElse(exception -> {
                            if (exception != null)
                                exception.printStackTrace();
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
    
}

```


### Contributing

Before contributing to this project, please note the following:

###### - Test your changes: Ensure that any changes you make to the project have been thoroughly tested before submitting them.

###### - Provide documentation: If you are adding new features or functionality, consider providing clear and concise documentation to help other contributors understand how to use them.

###### - Follow established standards: Adhere to established coding standards and best practices to ensure that your contributions are consistent with the rest of the project.

###### - Submit changes appropriately: Follow the project's preferred method of submitting changes, whether that is through pull requests, commits, or another method specified by the project owner.


#### <p align="center">Thanks for reading & reviewing my project!</p>
##### <p align="center"> Special thanks to <a href="https://github.com/AkramLZ">@AkramLZ</a> for inspiration!</p>





