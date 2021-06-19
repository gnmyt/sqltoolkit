package de.gnmyt.SQLToolkit.drivers;

import de.gnmyt.SQLToolkit.api.SQLConsumer;
import de.gnmyt.SQLToolkit.manager.InsertManager;
import de.gnmyt.SQLToolkit.manager.ResultManager;
import de.gnmyt.SQLToolkit.manager.DataBaseSelection;
import de.gnmyt.SQLToolkit.manager.UpdateManager;
import de.gnmyt.SQLToolkit.types.LogLevelType;
import de.gnmyt.SQLToolkit.types.LoginParam;

import java.sql.*;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public class MySQLConnection {

    private final String hostname;
    private final String username;
    private final String password;
    private final String database;
    private String tablePrefix = "";
    private String tablePrefixVariable = "";
    private String connectString = "";
    public static Connection con;
    private final SqlLogManager sqlLogManager;

    /**
     * Update the Connection String
     * @param loginParams New login parameters
     * @return this class
     */
    public MySQLConnection updateConnectString(LoginParam... loginParams) {
        this.connectString = getConnectionString(loginParams);
        return this;
    }

    /**
     * Set the Connection String
     * @param tablePrefixVariable New table prefix variable
     * @return this class
     */
    public MySQLConnection setTablePrefixVariable(String tablePrefixVariable) {
        this.tablePrefixVariable = tablePrefixVariable;
        return this;
    }

    /**
     * Set the current table Prefix
     * @param tablePrefix New table prefix
     * @return this class
     */
    public MySQLConnection setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    /**
     * Get the current table Prefix
     * @return the prefix
     */
    public String getTablePrefix() {
        return tablePrefix;
    }

    /**
     * Get the current jdbc connection string for mysql
     * @param loginParams login parameters
     * @return the string
     */
    private String getConnectionString(LoginParam[] loginParams) {
        boolean used = false;StringBuilder currentString = new StringBuilder();
        for (LoginParam param : loginParams) {
            String currentChar = (used) ? "&" : "?";used = true;
            currentString.append(currentChar).append(param.getValue());
        }
        return currentString.toString();
    }

    /**
     * Check if you are connected
     * @return status of the connection
     */
    public boolean isConnected() {
        return (con != null);
    }

    /**
     * Get the SQL Log Manager and adjust it how you like it
     * @return the SQL Log Manager
     */
    public SqlLogManager getLogManager() {
        return sqlLogManager;
    }

    /**
     * Basic constructor for the Connection
     * @param hostname MySQL server hostname
     * @param username MySQL server username
     * @param password MySQL server password
     * @param database MySQL server database
     * @param logLevel Logging level
     * @param loginParams Login parameters
     */
    public MySQLConnection(String hostname, String username, String password, String database, LogLevelType logLevel, LoginParam... loginParams) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.database = database;
        this.sqlLogManager = new SqlLogManager();
        updateConnectString(loginParams);
        setLogLevelType(logLevel);
    }

    /**
     * Basic constructor for the Connection
     * @param hostname MySQL server hostname
     * @param username MySQL server username
     * @param password MySQL server password
     * @param database MySQL server database
     * @param loginParams Login parameters
     */
    public MySQLConnection(String hostname, String username, String password, String database, LoginParam... loginParams) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.database = database;
        this.sqlLogManager = new SqlLogManager();
        updateConnectString(loginParams);
        setLogLevelType(LogLevelType.NONE);
    }

    /**
     * Set a new logging level
     * @param logLevelType New logging level
     * @return this class
     */
    public MySQLConnection setLogLevelType(LogLevelType logLevelType) {
        this.sqlLogManager.setLogLevelType(logLevelType);
        return this;
    }

    /**
     * Get a result from your server
     * @param query Search query
     * @param params Optional parameters
     * @return ResultSet
     */
    public ResultSet getResultSet(String query, Object... params) {
        query = (tablePrefix.isEmpty()) ? query : query.replace((tablePrefixVariable.isEmpty()) ? "$tp" : tablePrefixVariable, tablePrefix);
        try { int start = 1;
            PreparedStatement ps = con.prepareStatement(query);
            for (Object current : params) { ps.setObject(start, current);start++; }
            return ps.executeQuery();
        } catch (Exception err) {
            sqlLogManager.sendError(err.getMessage());
        }
        return null;
    }

    /**
     * Get a result from your server (get the Manager)
     * @param query Search query
     * @param params Optional parameters
     * @return ResultManager
     */
    public ResultManager getResult(String query, Object... params) {
        return new ResultManager(getResultSet(query, params), sqlLogManager);
    }

    /**
     * Run a action with a result from your server
     * @param query Search query
     * @param consumer consumer
     * @param params Optional parameters
     */
    public void getResult(String query, SQLConsumer<ResultSet> consumer, Object... params) {
        try {
            ResultSet resultSet = getResultSet(query, params);
            consumer.accept(resultSet);
            resultSet.close();
        } catch (Exception ignored) {}
    }

    /**
     * Update something on your server by query
     * @param query Update query
     * @param params Optional parameters
     * @return this class
     */
    public MySQLConnection update(String query, Object... params) {
        query = (tablePrefix.isEmpty()) ? query : query.replace((tablePrefixVariable.isEmpty()) ? "$tp" : tablePrefixVariable, tablePrefix);
        try { int start = 1;
            PreparedStatement ps = con.prepareStatement(query);
            for (Object current : params) { ps.setObject(start, current);start++; }
            ps.executeUpdate();
        } catch (Exception err) { err.printStackTrace(); }
        return this;
    }

    /**
     * Get the update de.gnmyt.SQLToolkit.manager for easier updating
     * @return Update de.gnmyt.SQLToolkit.manager
     */
    public UpdateManager update() {
        return new UpdateManager(this);
    }

    /**
     * Get the update de.gnmyt.SQLToolkit.manager for easier updating (pre filled table)
     * @param tableName The name of the table
     * @return Update de.gnmyt.SQLToolkit.manager
     */
    public UpdateManager updateTable(String tableName) {
        return new UpdateManager(this, tableName);
    }

    /**
     * Get the Database Selection for easier selection of tables (pre filled table)
     * @param tableName The name of the table
     * @return DatabaseSelection
     */
    public DataBaseSelection selectFrom(String tableName) {
        return new DataBaseSelection(this, tableName);
    }

    /**
     * Get the Database Selection for easier selection of tables
     * @return DatabaseSelection
     */
    public DataBaseSelection select() {
        return new DataBaseSelection(this);
    }

    /**
     * Get the InsertManager for easier inserting to a table
     * @return InsertManager
     */
    public InsertManager insert() {
        return new InsertManager(this);
    }

    /**
     * Get the InsertManager for easier inserting to a table
     * @param tableName The name of the table you want to insert a object
     * @return InsertManager
     */
    public InsertManager insertFrom(String tableName) {
        return new InsertManager(this, tableName);
    }

    /**
     * Connect with your MySQL server
     * @return this class
     */
    public MySQLConnection connect() {
        if (!isConnected()) { try {
            sqlLogManager.sendInfo("Connecting to " + hostname + " with user " + username + " to database " + database);
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + database + connectString, username, password);
                sqlLogManager.sendInfo("Connection established");
            } catch (Exception exception) {
                if (exception.getMessage().contains("jdbc.Driver"))
                    sqlLogManager.sendError("MySQL driver not found! Check your dependencies or install the JDBC Mysql Driver from https://dev.mysql.com/downloads/file/?id=498587");
                 else if (exception.getMessage().contains("has not received any packets")) sqlLogManager.sendError("No packets received from the server.");
                 else if (exception.getMessage().contains("denied for user")) sqlLogManager.sendError("Wrong username/password");
                 else sqlLogManager.sendError(exception.getMessage());
            }
        } else sqlLogManager.sendWarning("Already connected");
        return this;
    }

    /**
     * Disconnect from your MySQL Server
     * @return this class
     */
    public MySQLConnection disconnect() {
        if (isConnected()) { try { con.close();con = null;sqlLogManager.sendInfo("Connection closed");
        } catch (Exception err) { sqlLogManager.sendError(err.getMessage()); }
        } else sqlLogManager.sendWarning("Not connected. Please connect first");
        return this;
    }

}
