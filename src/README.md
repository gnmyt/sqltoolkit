[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]



<br />
<p align="center">
  <a href="https://github.com/germannewsmaker/sqltoolkit">
    <img src="https://i.imgur.com/BAvJgQN.png" alt="Logo" width="80" height="80">
  </a>
</p>
<h3 align="center">MySQL Toolkit</h3>

## About The Project
This is a small project for quickly managing a MySQL database in Java. It makes everyday life with a database much easier.

### Installation 
1. Clone the repo
   ```sh
   git clone https://github.com/germannewsmaker/sqltoolkit.git
   ```
2. Move the project into a package of your project (in most cases "sql")
3. Ready! Have fun
#### Maven comming soon

### Usage Examples
1. Create a connection
   - Example of a constructor without optional specifications
   ```java
   MySQLConnection connection = new MySQLConnection(hostname, username, password, database).connect();
   ```
   - Example of a constructor with setting the logging level
   ```java
   MySQLConnection connection = new MySQLConnection(hostname, username, password, database, LogLevelType.ALL).connect();
   ```
   - Example of a constructor with optional login parameters
   ```java
   MySQLConnection connection = new MySQLConnection(hostname, username, password, database, LoginParam.AUTO_RECONNECT, LoginParam.NO_SSL).connect();
   ```
   #### Logging Levels
   - NONE - Sends nothing
   - LOW - Sends Warnings & Errors
   - ALL - Sends Infos, Warnings & Errors
   #### Login Parameters
   - DEFAULT *(useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&useTimezone=true&serverTimezone=UTC)*
   - NO_SSL *(useSSL=false)*
   - USE_SSL *(useSSL=true)*
   - AUTO_RECONNECT *(autoReconnect=true)*
   - UTF8_ENCODING *(characterEncoding=UTF-8)*
   - USE_UNICODE *(useUnicode=yes)*
   - USE_TIMEZONE *(useTimezone=true)*
   - TIMEZONE_UTC *(serverTimezone=UTC)*
2. Perform a standard SQL query
   - Get a ResultSet
   ```java
   connection.getResultSet("default query", "parameters");
   ```
   - Perform an update
   ```java
   connection.update("query", "parameters");
   ```
3. Get something from a table with managers
   1. Getting a Result (For one result)
      ```java
      String value = connection.getResult("query", "parameters")
                               .getString("column");
      ```
   2. Getting Results (For more than one)
      ```java
      ArrayList<String> list = connection.getResult("query", "parameters")
                                         .getList("column");
      ```
   3. Choosing Results
      ```java
      connection
            .selectFrom("table")
            .where("column", "value")
            .limit(10)
            .getResult();
      ```
   4. Choosing Results + Print the current statement
      ```java
      connection.select()
             .from("table")
             .where("column", "value")
             .add("LIMIT 2,5")
             .printStatement();
      ```
4. Perform an update using managers
   1. Update a Table
      ```java
      connection
            .update()
            .toTable("table")
            .where("column", "value")
            .set("column", "newValue")
            .update();
      ```
   2. Generate a Table
      ```java
      connection
            .update()
            .generateTable("table")
            .useID()
            .addField(new SQLField(SQLType.STRING, "column", 999))
            .addField(new SQLField(SQLType.STRING, "column2", 25))
            .create();
      ```
   
   
## License

Distributed under the MIT License. See `LICENSE` for more information.

## End
Currently there are not many features yet, so feel free to write me some suggestions!

[contributors-shield]: https://img.shields.io/github/contributors/germannewsmaker/sqltoolkit.svg?style=for-the-badge
[contributors-url]: https://github.com/germannewsmaker/sqltoolkit/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/germannewsmaker/sqltoolkit.svg?style=for-the-badge
[forks-url]: https://github.com/germannewsmaker/sqltoolkit/network/members
[stars-shield]: https://img.shields.io/github/stars/germannewsmaker/sqltoolkit.svg?style=for-the-badge
[stars-url]: https://github.com/germannewsmaker/sqltoolkit/stargazers
[issues-shield]: https://img.shields.io/github/issues/germannewsmaker/sqltoolkit.svg?style=for-the-badge
[issues-url]: https://github.com/germannewsmaker/sqltoolkit/issues
[license-shield]: https://img.shields.io/github/license/germannewsmaker/sqltoolkit.svg?style=for-the-badge
[license-url]: https://github.com/germannewsmaker/sqltoolkit/blob/master/LICENSE.txt