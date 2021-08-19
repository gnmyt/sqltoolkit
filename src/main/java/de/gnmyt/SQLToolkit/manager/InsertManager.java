package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(tableName).append(" ").append("(");
        AtomicBoolean used = new AtomicBoolean(false);
        values.forEach((field, object) -> {
            if (used.get()) query.append(", ");
            used.set(true);
            query.append("`").append(field).append("`");
        });
        query.append(")");
        if (values.size() > 0) query.append(" VALUES (");
        AtomicBoolean used_values = new AtomicBoolean(false);
        for (int i = 0; i < values.size(); i++) {
            if (used_values.get()) query.append(", ");
            used_values.set(true);
            query.append("?");
        }
        if (values.size() > 0) query.append(")");
        return query.toString();
    }

    /**
     * Execute the current SQL query
     *
     * @return this class
     */
    public InsertManager execute() {
        connection.update(prepareStatement(), values.values().toArray());
        return this;
    }

}
