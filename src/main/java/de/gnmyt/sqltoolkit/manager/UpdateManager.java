package de.gnmyt.sqltoolkit.manager;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.UpdateQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import org.slf4j.Logger;

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
        this.tableName = tableName;
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
     */
    public void execute() {
        connection.update(build());
    }

    /**
     * Builds the current statement
     *
     * @return the current statement
     */
    public SQLQuery build() {
        return connection.createQuery(UpdateQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName)
                .addParameter(QueryParameter.WHERE_LIST, whereList)
                .addParameter(QueryParameter.SET_LIST, setList)
                .build();
    }

}
