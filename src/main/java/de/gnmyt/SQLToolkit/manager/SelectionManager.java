package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SelectionManager {

    private final Logger LOG = MySQLConnection.LOG;

    private final MySQLConnection connection;
    private final HashMap<String, Object> whereList;
    private final ArrayList<Object> databaseParameters;
    public ArrayList<String> optionalQuery;
    private int limit;
    private String tableName;

    /**
     * Basic constructor for selection
     *
     * @param connection The current Connection
     * @param tableName  The table name
     */
    public SelectionManager(MySQLConnection connection, String tableName) {
        this.whereList = new HashMap<>();
        this.databaseParameters = new ArrayList<>();
        this.optionalQuery = new ArrayList<>();
        this.connection = connection;
        from(tableName);
    }

    /**
     * Basic constructor for selection
     *
     * @param connection The current Connection
     */
    public SelectionManager(MySQLConnection connection) {
        this.whereList = new HashMap<>();
        this.databaseParameters = new ArrayList<>();
        this.optionalQuery = new ArrayList<>();
        this.connection = connection;
    }

    /**
     * Get the temp-generated parameters
     *
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
     *
     * @param tableName The table name
     * @return this class
     */
    public SelectionManager from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adding another 'where'-statement
     *
     * @param column Table column name
     * @param value  Table value name
     * @return this class
     */
    public SelectionManager where(String column, Object value) {
        whereList.put(column, value);
        return this;
    }

    /**
     * Sets the limit of the rows
     *
     * @param limit The new limit
     * @return this class
     */
    public SelectionManager limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Sets the limit of the rows
     *
     * @param limit The new limit
     * @return this class
     */
    public SelectionManager limit(String limit) {
        this.limit = Integer.parseInt(limit);
        return this;
    }

    /**
     * Get the ResultManager
     *
     * @return ResultManager
     */
    public ResultManager getResult() {
        return connection.getResult(processStatement(), getTempParams().toArray());
    }

    /**
     * Get the ResultSet
     *
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return connection.getResultSet(processStatement(), getTempParams().toArray());
    }

    /**
     * Adding another factors
     *
     * @param query  MySQL Query
     * @param params Optional parameters for the Query
     * @return this class
     */
    public SelectionManager add(String query, Object... params) {
        optionalQuery.add(query);
        this.databaseParameters.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * Get the current statement query
     *
     * @return the current statement query
     */
    private String processStatement() {
        StringBuilder query = new StringBuilder().append("SELECT * FROM ").append(tableName).append(" ");

        for (int i = 0; i < whereList.size(); i++) {
            if (i > 0) query.append("WHERE ");
            else query.append("AND ");

            query.append(whereList.keySet().toArray()[i]).append(" = ? ");
        }

        if (limit != 0) query.append("LIMIT ").append(limit);
        optionalQuery.forEach(v -> query.append(" ").append(v).append(" "));
        return query.toString();
    }

}
