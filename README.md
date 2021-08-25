[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]



<br />
<p align="center">
  <a href="https://github.com/gnmyt/sqltoolkit">
    <img src="https://i.imgur.com/BAvJgQN.png" alt="Logo" width="80" height="80">
  </a>
</p>
<h3 align="center">MySQL Toolkit</h3>

## About The Project

This is a small project for quickly managing a MySQL database in Java. It makes everyday life with a database much
easier.

### Installation

1. Add the jitpack repository to your `pom.xml`
   ```xml
   <repositories>
      <repository>
         <id>jitpack.io</id>
         <url>https://jitpack.io</url>
      </repository>
   </repositories>
   ```
2. Add the dependency to your `pom.xml`
   ```xml
   <dependency>
	    <groupId>com.github.gnmyt</groupId>
	    <artifactId>sqltoolkit</artifactId>
	    <version>master-SNAPSHOT</version>
   </dependency>
   ```

### Usage Examples

1. Create a connection
    - Example of a constructor without optional specifications
   ```java
   MySQLConnection connection = new MySQLConnection(hostname, username, password, database).connect();
   ```
    - Example of a constructor with optional login parameters
   ```java
   MySQLConnection connection = new MySQLConnection(hostname, username, password, database, LoginParam.AUTO_RECONNECT, LoginParam.NO_SSL).connect();
   ```
   #### Login Parameters
    - DEFAULT *(
      useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&useTimezone=true&serverTimezone=UTC)*
    - NO_SSL *(useSSL=false)*
    - USE_SSL *(useSSL=true)*
    - AUTO_RECONNECT *(autoReconnect=true)*
    - UTF8_ENCODING *(characterEncoding=UTF-8)*
    - USE_UNICODE *(useUnicode=yes)*
    - USE_TIMEZONE *(useTimezone=true)*
    - TIMEZONE_UTC *(serverTimezone=UTC)*
2. Perform a default SQL query
    - Get a ResultSet
      ```java
      connection.getResultSet("SELECT * FROM example WHERE test = ?", "test1");
      ```
    - Perform an update
      ```java
      connection.update("UPDATE example SET test = ? WHERE abc = ?", "test1", "test2");
      ```
3. Get something from a table with managers
    1. Getting a string from the table
       ```java
       String value = connection.getResult("query", "parameters")
                                .getString("column");
       ```
    2. Getting a list from the table
       ```java
       ArrayList<String> list = connection.getResult("query", "parameters")
                                          .getList("column");
       ```
       or
       ```java
       ArrayList<HashMap<String, Object>> list = connection.getResult("query", "parameters")
                                          .getList();
       ```
    4. Choosing Results
       ```java
       connection
             .selectFrom("table")
             .where("column", "value")
             .limit(10)
             .getResult();
       ```
    5. Choosing Results with custom parameters
       ```java
       connection.select()
              .from("table")
              .where("column", "value")
              .add("LIMIT 2,5")
              .getResult();
       ```
4. Perform an update using managers
    1. Update a Table
       ```java
       connection
             .updateTo("table")
             .where("column", "value")
             .set("column", "newValue")
             .execute();
       ```
    2. Generate a Table
       ```java
       connection
             .generateTable("table")
             .addField(SQLType.STRING, "column", 999)
             .addField(SQLType.STRING, "column2", 25)
             .create();
       ```
    3. Delete something from a table
       ```java
       connection
             .deleteFrom("table")
             .where("column", "value")
             .execute();
       ```
You can find other examples in the [examples directory](src/examples/java).

## License

Distributed under the MIT License. See `LICENSE` for more information.

## End

Currently, there are not many features yet, so feel free to write me some suggestions!

[contributors-shield]: https://img.shields.io/github/contributors/gnmyt/sqltoolkit.svg?style=for-the-badge

[contributors-url]: https://github.com/gnmyt/sqltoolkit/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/gnmyt/sqltoolkit.svg?style=for-the-badge

[forks-url]: https://github.com/gnmyt/sqltoolkit/network/members

[stars-shield]: https://img.shields.io/github/stars/gnmyt/sqltoolkit.svg?style=for-the-badge

[stars-url]: https://github.com/gnmyt/sqltoolkit/stargazers

[issues-shield]: https://img.shields.io/github/issues/gnmyt/sqltoolkit.svg?style=for-the-badge

[issues-url]: https://github.com/gnmyt/sqltoolkit/issues

[license-shield]: https://img.shields.io/github/license/gnmyt/sqltoolkit.svg?style=for-the-badge

[license-url]: https://github.com/gnmyt/sqltoolkit/blob/master/LICENSE