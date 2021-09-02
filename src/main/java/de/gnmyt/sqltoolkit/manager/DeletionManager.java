package de.gnmyt.sqltoolkit.manager;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.DeletionQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import org.slf4j.Logger;

import java.util.HashMap;

public class DeletionManager {

    private final Logger LOG = MySQLConnection.LOG;

    private final MySQLConnection connection;

    private final HashMap<String, Object> whereList = new HashMap<>();
    private String tableName;

    /**
     * Advanced constructor for the {@link DeletionManager}
     *
     * @param connection The current connection
     * @param tableName  The table name
     */
    public DeletionManager(MySQLConnection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * Basic constructor for the {@link DeletionManager}
     *
     * @param connection The current connection
     */
    public DeletionManager(MySQLConnection connection) {
        this.connection = connection;
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
        connection.update(build());
    }

    /**
     * Builds the current statement
     *
     * @return the current statement
     */
    private SQLQuery build() {
        return connection.createQuery(DeletionQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName)
                .addParameter(QueryParameter.WHERE_LIST, whereList).build();
    }

}
