package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import de.gnmyt.SQLToolkit.generator.TableGenerator;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * Get the current connection
     *
     * @return MySQL connection
     */
    public MySQLConnection getConnection() {
        return connection;
    }

    /**
     * Print the deletion statement
     *
     * @return this class
     */
    public UpdateManager printDeleteStatement() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(processDeleteStatement());
        messageBuilder.append(" | params» ");
        AtomicBoolean added = new AtomicBoolean(false);
        whereList.values().forEach(v -> {
            messageBuilder.append((added.get()) ? ", " : "").append(v);
            added.set(true);
        });
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement stack = st[st.length - 1];
        LOG.debug("DEBUG <" + stack.getFileName() + ":" + stack.getLineNumber() + "> Statement: " + messageBuilder);
        return this;
    }

    /**
     * Print the update statement
     *
     * @return this class
     */
    public UpdateManager printUpdateStatement() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(processUpdateStatement());
        messageBuilder.append(" | params» ");
        AtomicBoolean added = new AtomicBoolean(false);
        getTempParams().forEach(v -> {
            messageBuilder.append((added.get()) ? ", " : "").append(v);
            added.set(true);
        });
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StackTraceElement stack = st[st.length - 1];
        LOG.debug("DEBUG <" + stack.getFileName() + ":" + stack.getLineNumber() + "> Statement: " + messageBuilder);
        return this;
    }

    /**
     * Change the tableName
     *
     * @param tableName New tableName
     * @return this class
     */
    public UpdateManager toTable(String tableName) {
        this.tableName = connection.getTablePrefix().isEmpty() ? tableName : connection.getTablePrefix() + tableName;
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
     * Delete the entries with your current conditions
     *
     * @return this class
     */
    public UpdateManager delete() {
        connection.update(processDeleteStatement(), whereList.values().toArray());
        return this;
    }

    /**
     * Update the entries with your current conditions
     *
     * @return this class
     */
    public UpdateManager update() {
        connection.update(processUpdateStatement(), getTempParams().toArray());
        return this;
    }

    /**
     * Get the Query of the 'update'-Statement
     *
     * @return the Statement
     */
    private String processUpdateStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName);
        if (!setList.isEmpty()) sb.append(" SET ");
        AtomicBoolean used = new AtomicBoolean(false);
        setList.forEach((str, obj) -> {
            if (used.get()) sb.append(", ");
            sb.append(str).append(" = ?");
            used.set(true);
        });
        if (!whereList.isEmpty()) sb.append(" WHERE ");
        AtomicBoolean used2 = new AtomicBoolean(false);
        whereList.forEach((str, obj) -> {
            if (used2.get()) sb.append(" AND ");
            sb.append(str).append(" = ?");
            used2.set(true);
        });
        return sb.toString();
    }

    /**
     * Get the Query of the 'delete'-Statement
     *
     * @return
     */
    private String processDeleteStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(tableName);
        if (!whereList.isEmpty()) sb.append(" WHERE ");
        AtomicBoolean used = new AtomicBoolean(false);
        whereList.forEach((str, obj) -> {
            if (used.get()) sb.append(" AND ");
            sb.append(str).append(" = ?");
            used.set(true);
        });
        return sb.toString();
    }


    /**
     * Generate a new Table (with prefilled tableName)
     *
     * @param tableName Name of the new table
     * @return the de.gnmyt.SQLToolkit.generator
     */
    public TableGenerator generateTable(String tableName) {
        return new TableGenerator(this.getConnection(), tableName);
    }

    /**
     * Generate a new Table
     *
     * @return the de.gnmyt.SQLToolkit.generator
     */
    public TableGenerator generateTable() {
        return (tableName.isEmpty()) ? null : new TableGenerator(this.getConnection(), tableName);
    }

}
