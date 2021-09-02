package de.gnmyt.sqltoolkit.manager;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.InsertQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;

import java.util.HashMap;

public class InsertManager {

    private final MySQLConnection connection;
    private final HashMap<String, Object> values;
    private String tableName;

    /**
     * Basic constructor for the InsertManager
     *
     * @param connection The current connection
     */
    public InsertManager(MySQLConnection connection) {
        this.connection = connection;
        values = new HashMap<>();
    }

    /**
     * Constructor with direct tableName integration
     *
     * @param connection The current connection
     * @param tableName  The name of the table
     */
    public InsertManager(MySQLConnection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        values = new HashMap<>();
    }

    /**
     * Optionally add the tableName / Change the name of the table
     *
     * @param tableName The name of the table
     */
    public void to(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Query value
     *
     * @param fieldName The name of the column
     * @param value     The value of the object you want to insert
     * @return this class
     */
    public InsertManager value(String fieldName, Object value) {
        values.put(fieldName, value);
        return this;
    }

    /**
     * Executes the current SQL query
     */
    public void execute() {
        connection.update(build());
    }

    /**
     * Builds the current statement
     *
     * @return the current statement
     */
    private SQLQuery build() {
        return connection.createQuery(InsertQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName)
                .addParameter(QueryParameter.VALUE_LIST, values)
                .build();
    }

}
