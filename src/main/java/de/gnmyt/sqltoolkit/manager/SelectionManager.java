package de.gnmyt.sqltoolkit.manager;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.SelectionQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryBuilder;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SelectionManager {

    private final Logger LOG = MySQLConnection.LOG;
    private final MySQLConnection connection;

    private final HashMap<String, Object> WHERE_LIST = new HashMap<>();
    private final ArrayList<String> SELECTION_LIST = new ArrayList<>();

    private String limit;
    private String tableName = "default";

    /**
     * Advanced constructor of the {@link SelectionManager} with a prefilled table name
     *
     * @param connection The current connection
     * @param tableName  The table name
     */
    public SelectionManager(MySQLConnection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    /**
     * Basic constructor of the {@link SelectionManager}
     *
     * @param connection The current connection
     */
    public SelectionManager(MySQLConnection connection) {
        this.connection = connection;
    }

    public SelectionManager select(String... selection) {
        SELECTION_LIST.addAll(Arrays.asList(selection));
        return this;
    }

    /**
     * Sets the table name of the selection
     *
     * @param tableName The new table name
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
        WHERE_LIST.put(column, value);
        return this;
    }

    /**
     * Sets the limit of the rows
     *
     * @param limit The new limit
     * @return this class
     */
    public SelectionManager limit(int limit) {
        this.limit = String.valueOf(limit);
        return this;
    }

    /**
     * Sets the limit of the rows
     *
     * @param startRow The first row that should be counted
     * @param limit    The new limit
     * @return this class
     */
    public SelectionManager limit(int startRow, int limit) {
        this.limit = startRow + "," + limit;
        return this;
    }

    /**
     * Get the ResultManager
     *
     * @return ResultManager
     */
    public ResultManager getResult() {
        return connection.getResult(build());
    }

    /**
     * Get the ResultSet
     *
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return connection.getResultSet(build());
    }

    /**
     * Builds the query
     *
     * @return the built query
     */
    private SQLQuery build() {
        QueryBuilder query = connection.createQuery(SelectionQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName)
                .addParameter(QueryParameter.LIMIT, limit)
                .addParameter(QueryParameter.WHERE_LIST, WHERE_LIST);

        if (!SELECTION_LIST.isEmpty()) query.addParameter(QueryParameter.SELECT_LIST, SELECTION_LIST);

        return query.build();
    }

}
