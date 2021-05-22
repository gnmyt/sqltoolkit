package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public class DataBaseSelection {

    private final MySQLConnection connection;
    private final HashMap<String, Object> whereList;
    private final ArrayList<Object> databaseParameters;
    public ArrayList<String> optionalQuery;
    private int limit;
    private String tableName;

    /**
     * Basic constructor for selection
     * @param connection The current Connection
     * @param tableName The table name
     */
    public DataBaseSelection(MySQLConnection connection, String tableName) {
        this.whereList = new HashMap<>();
        this.databaseParameters = new ArrayList<>();
        this.optionalQuery = new ArrayList<>();
        this.connection = connection;
        from(tableName);
    }

    /**
     * Basic constructor for selection
     * @param connection The current Connection
     */
    public DataBaseSelection(MySQLConnection connection) {
        this.whereList = new HashMap<>();
        this.databaseParameters = new ArrayList<>();
        this.optionalQuery = new ArrayList<>();
        this.connection = connection;
    }

    /**
     * Get the temp-generated parameters
     * @return ArrayList of parameters
     */
    private ArrayList<Object> getTempParams() {
        ArrayList<Object> tempParameters = new ArrayList<>();
        whereList.forEach((k, v) -> tempParameters.add(v));
        tempParameters.addAll(databaseParameters);
        return tempParameters;
    }

    /**
     * Sets the table name
     * @param tableName The table name
     * @return this class
     */
    public DataBaseSelection from(String tableName) {
        this.tableName = connection.getTablePrefix().isEmpty() ? tableName : connection.getTablePrefix() + tableName;
        return this;
    }

    /**
     * Adding another 'where'-statement
     * @param column Table column name
     * @param value Table value name
     * @return this class
     */
    public DataBaseSelection where(String column, Object value) {
        whereList.put(column, value);
        return this;
    }

    /**
     * Sets the limit of the rows
     * @param limit The new limit
     * @return this class
     */
    public DataBaseSelection limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Sets the limit of the rows
     * @param limit The new limit
     * @return this class
     */
    public DataBaseSelection limit(String limit) {
        this.limit = Integer.parseInt(limit);
        return this;
    }

    /**
     * Get the ResultManager
     * @return ResultManager
     */
    public ResultManager getResult() {
        return connection.getResult(processStatement(), getTempParams().toArray());
    }

    /**
     * Get the ResultSet
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return connection.getResultSet(processStatement(), getTempParams().toArray());
    }

    /**
     * Debug the current Statement
     * @return this class
     */
    public DataBaseSelection printStatement() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(processStatement());
        messageBuilder.append(" | params» ");
        AtomicBoolean added = new AtomicBoolean(false);
        getTempParams().forEach(v -> { messageBuilder.append((added.get()) ? ", " : "").append(v);added.set(true); });
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement stack = st[st.length-1];
        connection.getLogManager()
                .sendInfo("DEBUG <" + stack.getFileName()+":"+stack.getLineNumber()  + "> Statement: " + messageBuilder.toString());
        return this;
    }

    /**
     * Adding another factors
     * @param query MySQL Query
     * @param params Optional parameters for the Query
     * @return this class
     */
    public DataBaseSelection add(String query, Object... params) {
        optionalQuery.add(query);
        this.databaseParameters.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * Get the current statement query
     * @return the current statement query
     */
    private String processStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" ");
        AtomicBoolean used = new AtomicBoolean(false);
        whereList.forEach((k, v) -> { if (!used.get()) sb.append("WHERE ");else sb.append("AND ");used.set(true);sb.append(k).append(" = ? "); });
        if (limit != 0) sb.append("LIMIT ").append(limit);
        optionalQuery.forEach(v -> sb.append(" ").append(v).append(" "));
        return sb.toString();
    }

}
