package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;

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
    public void from(String tableName) {
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
     * Prepares the SQL Query
     *
     * @return the SQL Query
     */
    public String prepareStatement() {
        StringBuilder query = new StringBuilder().append("INSERT INTO ").append(tableName).append(" (");

        for (int i = 0; i < values.size(); i++) {
            if (i > 0) query.append(", ");
            query.append("`").append(values.keySet().toArray()[i]).append("`");
        }

        query.append(")").append(values.size() > 0 ? "VALUES (" : "");

        for (int i = 0; i < values.size(); i++) {
            if (i > 0) query.append(", ");
            query.append("?");
        }

        if (values.size() > 0) query.append(")");

        return query.toString();
    }


    /**
     * Executes the current SQL query
     */
    public void execute() {
        connection.update(prepareStatement(), values.values().toArray());
    }

}
