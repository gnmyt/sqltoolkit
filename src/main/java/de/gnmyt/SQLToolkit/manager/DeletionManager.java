package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DeletionManager {

    private final Logger LOG = MySQLConnection.LOG;

    private final MySQLConnection connection;
    private final HashMap<String, Object> whereList;
    private final ArrayList<Object> databaseParameters;
    public ArrayList<String> optionalQuery;
    private String tableName;

    /**
     * Advanced constructor for the {@link DeletionManager}
     *
     * @param connection The current connection
     * @param tableName  The table name
     */
    public DeletionManager(MySQLConnection connection, String tableName) {
        this.whereList = new HashMap<>();
        this.databaseParameters = new ArrayList<>();
        this.optionalQuery = new ArrayList<>();
        this.connection = connection;
        from(tableName);
    }

    /**
     * Basic constructor for the {@link DeletionManager}
     *
     * @param connection The current connection
     */
    public DeletionManager(MySQLConnection connection) {
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
    public DeletionManager from(String tableName) {
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
    public DeletionManager where(String column, Object value) {
        whereList.put(column, value);
        return this;
    }

    /**
     * Executes the 'delete'-statement
     */
    public void execute() {
        connection.update(prepareStatement(), getTempParams());
    }

    /**
     * Adding another factors
     *
     * @param query  MySQL Query
     * @param params Optional parameters for the Query
     * @return this class
     */
    public DeletionManager add(String query, Object... params) {
        optionalQuery.add(query);
        this.databaseParameters.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * Get the current statement query
     *
     * @return the current statement query
     */
    private String prepareStatement() {
        StringBuilder query = new StringBuilder().append("DELETE FROM ").append(tableName);

        if (!whereList.isEmpty()) query.append(" WHERE ");

        for (int i = 0; i < whereList.size(); i++) {
            if (i > 0) query.append(" AND ");
            query.append(whereList.keySet().toArray()[i]).append(" = ?");
        }

        return query.toString();
    }

}
