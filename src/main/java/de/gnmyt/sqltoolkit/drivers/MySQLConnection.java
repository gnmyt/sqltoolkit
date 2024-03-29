package de.gnmyt.sqltoolkit.drivers;

import de.gnmyt.sqltoolkit.api.SQLConsumer;
import de.gnmyt.sqltoolkit.factory.TableFactory;
import de.gnmyt.sqltoolkit.generator.TableGenerator;
import de.gnmyt.sqltoolkit.manager.*;
import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryBuilder;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLConnection {

    public static final Logger LOG = LoggerFactory.getLogger("MySQL-Logger");
    private final TableFactory tableFactory = new TableFactory(this);
    private final ConnectSettingsManager settingsManager = new ConnectSettingsManager(this);

    private final String hostname;
    private final String username;
    private final String password;
    private final String database;

    protected Connection con;

    /**
     * Basic constructor for the connection
     *
     * @param hostname MySQL server hostname
     * @param username MySQL server username
     * @param password MySQL server password
     * @param database MySQL server database
     */
    public MySQLConnection(String hostname, String username, String password, String database) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public ConnectSettingsManager updateSettings() {
        return settingsManager;
    }

    /**
     * Get a result from your server
     *
     * @param query  Search query
     * @param params Optional parameters
     * @return ResultSet
     */
    public ResultSet getResultSet(String query, Object... params) {
        try {
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeQuery();
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return null;
    }

    /**
     * Get a result from your server (get the manager)
     *
     * @param query  Search query
     * @param params Optional parameters
     * @return ResultManager
     */
    public ResultManager getResult(String query, Object... params) {
        return new ResultManager(getResultSet(query, params));
    }

    /**
     * Run an action with a result from your server
     *
     * @param query    The search query
     * @param consumer The consumer
     * @param params   The optional parameters
     */
    public void getResultSet(String query, SQLConsumer<ResultSet> consumer, Object... params) {
        try {
            consumer.accept(getResultSet(query, params));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Run an action with a result from your server
     *
     * @param query    The search query
     * @param consumer The consumer
     * @param params   The optional parameters
     */
    public void getResult(String query, SQLConsumer<ResultManager> consumer, Object... params) {
        try {
            consumer.accept(getResult(query, params));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Gets a {@link ResultSet} from the server
     *
     * @param query The query you want to execute
     * @return the {@link ResultSet}
     */
    public ResultSet getResultSet(SQLQuery query) {
        return getResultSet(query.getStatement(), query.getParameters());
    }

    /**
     * Gets a {@link ResultManager} from the server
     *
     * @param query The query you want to execute
     * @return the {@link ResultManager}
     */
    public ResultManager getResult(SQLQuery query) {
        return getResult(query.getStatement(), query.getParameters());
    }

    /**
     * Run an action with the result from your server
     *
     * @param query    The query you want to execute
     * @param consumer The consumer you want to execute
     */
    public void getResultSet(SQLQuery query, SQLConsumer<ResultSet> consumer) {
        try {
            consumer.accept(getResultSet(query));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Run an action with the result from your server (get the manager)
     *
     * @param query    The query you want to execute
     * @param consumer The consumer you want to execute
     */
    public void getResult(SQLQuery query, SQLConsumer<ResultManager> consumer) {
        try {
            consumer.accept(getResult(query));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Gets a new instance of the {@link QueryBuilder}
     *
     * @param queryType The type of the query you want to generate
     * @return a new {@link QueryBuilder} instance
     */
    public QueryBuilder createQuery(Class<? extends AbstractQuery> queryType) {
        return new QueryBuilder(queryType);
    }

    /**
     * Update something on your server by query
     *
     * @param query  Update query
     * @param params Optional parameters
     * @return this class
     */
    public MySQLConnection update(String query, Object... params) {
        try {
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ps.executeUpdate();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return this;
    }

    /**
     * Update something on your server by query
     *
     * @param query The query you want to execute
     * @return this class
     */
    public MySQLConnection update(SQLQuery query) {
        return update(query.getStatement(), query.getParameters());
    }

    /**
     * Gets the {@link UpdateManager} for easier updating
     *
     * @return the {@link UpdateManager}
     */
    public UpdateManager update() {
        return new UpdateManager(this);
    }

    /**
     * Gets the {@link UpdateManager} for easier updating (pre-filled table)
     *
     * @param tableName The name of the table
     * @return the {@link UpdateManager}
     */
    public UpdateManager updateTo(String tableName) {
        return new UpdateManager(this, tableName);
    }

    /**
     * Gets the {@link SelectionManager} for easier selection of tables
     *
     * @return The {@link SelectionManager}
     */
    public SelectionManager select() {
        return new SelectionManager(this);
    }

    /**
     * Gets the {@link SelectionManager} for easier selection of tables (pre-filled table)
     *
     * @param tableName The name of the table
     * @return the {@link SelectionManager}
     */
    public SelectionManager selectFrom(String tableName) {
        return new SelectionManager(this, tableName);
    }

    /**
     * Gets the {@link InsertManager} for easier inserting to a table
     *
     * @return the {@link InsertManager}
     */
    public InsertManager insert() {
        return new InsertManager(this);
    }

    /**
     * Gets the {@link InsertManager} for easier inserting to a table
     *
     * @param tableName The name of the table you want to insert a object
     * @return the {@link InsertManager}
     */
    public InsertManager insertTo(String tableName) {
        return new InsertManager(this, tableName);
    }

    /**
     * Gets the {@link DeletionManager} for easier deleting rows in a table
     *
     * @return the {@link DeletionManager}
     */
    public DeletionManager delete() {
        return new DeletionManager(this);
    }

    /**
     * Gets the {@link DeletionManager} for easier deleting rows in a table
     *
     * @param tableName The name of the table you want to delete a row from
     * @return the {@link DeletionManager}
     */
    public DeletionManager deleteFrom(String tableName) {
        return new DeletionManager(this, tableName);
    }

    /**
     * Gets the table generator
     *
     * @param tableName The name of the table
     * @return the instance of the table generator
     */
    public TableGenerator generateTable(String tableName) {
        return new TableGenerator(this, tableName);
    }

    /**
     * Gets the table factory
     *
     * @return the table factory
     */
    public TableFactory getTableFactory() {
        return tableFactory;
    }

    /**
     * Connect with your MySQL server
     *
     * @return this class
     */
    public MySQLConnection connect() {
        if (!isConnected()) {
            try {
                LOG.info("Connecting to " + hostname + " with user " + username + " to database " + database);
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + database + settingsManager.generateConnectionString(), username, password);
                LOG.info("Connection established");
            } catch (Exception exception) {
                if (exception.getMessage().contains("jdbc.Driver"))
                    LOG.error("MySQL driver not found! Check your dependencies or install the JDBC Mysql Driver from https://dev.mysql.com/downloads/file/?id=498587");
                else if (exception.getMessage().contains("has not received any packets"))
                    LOG.error("No packets received from the server.");
                else if (exception.getMessage().contains("denied for user"))
                    LOG.error("Wrong username/password");
                else LOG.error(exception.getMessage());
            }
        } else LOG.warn("Already connected");
        return this;
    }

    /**
     * Disconnect from your MySQL Server
     *
     * @return this class
     */
    public MySQLConnection disconnect() {
        if (isConnected()) {
            try {
                con.close();
                con = null;
                LOG.info("Connection closed");
            } catch (Exception err) {
                LOG.error(err.getMessage());
            }
        } else LOG.warn("Not connected. Please connect first");
        return this;
    }

    /**
     * Check if you are connected
     *
     * @return status of the connection
     */
    public boolean isConnected() {
        return (con != null);
    }


}
