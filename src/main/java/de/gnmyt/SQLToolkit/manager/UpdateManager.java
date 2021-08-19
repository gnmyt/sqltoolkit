package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateManager {

    private final Logger LOG = MySQLConnection.LOG;

    private final MySQLConnection connection;
    private final HashMap<String, Object> whereList;
    private final HashMap<String, Object> setList;
    private String tableName;

    /**
     * Basic constructor for the UpdateManager
     *
     * @param connection Existing MySQL connection
     */
    public UpdateManager(MySQLConnection connection) {
        this.connection = connection;
        this.whereList = new HashMap<>();
        this.setList = new HashMap<>();
    }

    /**
     * Basic constructor for the UpdateManager
     *
     * @param connection Existing MySQL connection
     * @param tableName  Table name
     */
    public UpdateManager(MySQLConnection connection, String tableName) {
        this.connection = connection;
        toTable(tableName);
        this.whereList = new HashMap<>();
        this.setList = new HashMap<>();
    }

    /**
     * Change the tableName
     *
     * @param tableName New tableName
     * @return this class
     */
    public UpdateManager toTable(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Get the parameters for the SQL statement
     *
     * @return the parameters
     */
    private ArrayList<Object> getTempParams() {
        ArrayList<Object> tempParams = new ArrayList<>();
        setList.forEach((str, obj) -> tempParams.add(obj));
        whereList.forEach((str, obj) -> tempParams.add(obj));
        return tempParams;
    }

    /**
     * Add a 'where'-clause
     *
     * @param column Table column
     * @param value  Value of the column
     * @return this class
     */
    public UpdateManager where(String column, Object value) {
        whereList.put(column, value);
        return this;
    }

    /**
     * Add a 'set'-clause
     *
     * @param column Table column
     * @param value  Value of the column
     * @return this class
     */
    public UpdateManager set(String column, Object value) {
        setList.put(column, value);
        return this;
    }

    /**
     * Update the entries with your current conditions
     *
     * @return this class
     */
    public UpdateManager execute() {
        connection.update(prepareUpdateStatement(), getTempParams().toArray());
        return this;
    }

    /**
     * Get the Query of the 'update'-Statement
     *
     * @return the Statement
     */
    private String prepareUpdateStatement() {
        StringBuilder query = new StringBuilder().append("UPDATE ").append(tableName);
        if (!setList.isEmpty()) query.append(" SET ");

        for (int i = 0; i < setList.size(); i++) {
            if (i > 0) query.append(", ");
            query.append(setList.keySet().toArray()[i]);
        }

        if (!whereList.isEmpty()) query.append(" WHERE ");

        for (int i = 0; i < whereList.size(); i++) {
            if (i > 0) query.append(" AND ");
            query.append(whereList.keySet().toArray()[i]).append(" = ?");
        }

        return query.toString();
    }

}
