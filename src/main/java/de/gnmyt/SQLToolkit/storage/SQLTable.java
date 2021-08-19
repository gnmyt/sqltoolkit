package de.gnmyt.SQLToolkit.storage;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import de.gnmyt.SQLToolkit.manager.DataBaseSelection;
import de.gnmyt.SQLToolkit.manager.InsertManager;
import de.gnmyt.SQLToolkit.manager.UpdateManager;
import de.gnmyt.SQLToolkit.types.SQLType;
import de.gnmyt.SQLToolkit.types.TableField;

import java.util.ArrayList;

public abstract class SQLTable {

    private final MySQLConnection connection;
    private ArrayList<TableField> tableFields;

    /**
     * The basic constructor of the {@link SQLTable}
     *
     * @param connection The mysql connection you want to use
     */
    public SQLTable(MySQLConnection connection) {
        this.connection = connection;
    }

    protected abstract String tableName();

    protected abstract void tableFields();

    /**
     * Adds a string to the table (without allowNull)
     *
     * @param name         The name of the string you want to add
     * @param length       The length of the string you want to add
     * @param defaultValue The default value of the string you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the string
     */
    protected void string(String name, int length, String defaultValue, String... extras) {
        custom(SQLType.STRING.getValue(), name, length, false, defaultValue, extras);
    }

    /**
     * Adds a string to the table (with allowNull)
     *
     * @param name         The name of the string you want to add
     * @param length       The length of the string you want to add
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the string you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the string
     */
    protected void string(String name, int length, boolean allowNull, String defaultValue, String... extras) {
        custom(SQLType.STRING.getValue(), name, length, allowNull, defaultValue, extras);
    }

    /**
     * Adds an integer to the table (without allowNull)
     *
     * @param name         The name of the integer you want to add
     * @param length       The length of the integer you want to add
     * @param defaultValue The default value of the integer you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the integer
     */
    protected void integer(String name, int length, String defaultValue, String... extras) {
        custom(SQLType.INTEGER.getValue(), name, length, false, defaultValue, extras);
    }

    /**
     * Adds an integer to the table (with allowNull)
     *
     * @param name         The name of the integer you want to add
     * @param length       The length of the integer you want to add
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the integer you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the integer
     */
    protected void integer(String name, int length, boolean allowNull, String defaultValue, String... extras) {
        custom(SQLType.INTEGER.getValue(), name, length, allowNull, defaultValue, extras);
    }

    /**
     * Adds a boolean to the table (without allowNull)
     *
     * @param name         The name of the boolean you want to add
     * @param length       The length of the boolean you want to add
     * @param defaultValue The default value of the boolean you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the boolean
     */
    protected void bool(String name, int length, String defaultValue, String... extras) {
        custom(SQLType.BOOLEAN.getValue(), name, length, false, defaultValue, extras);
    }

    /**
     * Adds a boolean to the table (with allowNull)
     *
     * @param name         The name of the boolean you want to add
     * @param length       The length of the boolean you want to add
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the boolean you want to add (leave empty if you don't want to use one)
     * @param extras       The extras you want to add to the boolean
     */
    protected void bool(String name, int length, boolean allowNull, String defaultValue, String... extras) {
        custom(SQLType.BOOLEAN.getValue(), name, length, allowNull, defaultValue, extras);
    }

    /**
     * Adds a custom field to the table
     *
     * @param type         The type of the field
     * @param name         The name of the field
     * @param length       The length of the field
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the field
     * @param extras       The extras that you want to add
     */
    protected void custom(String type, String name, int length, boolean allowNull, String defaultValue, String... extras) {
        custom(new TableField(name, type, length, allowNull, defaultValue).setExtra(extras));
    }

    /**
     * Gets the database selection from the current the table
     * @return the database selection
     */
    public DataBaseSelection select() {
        return connection.selectFrom(tableName());
    }

    /**
     * Gets the update manager of the current table
     * @return the update manager
     */
    public UpdateManager update() {
        return connection.updateTable(tableName());
    }

    /**
     * Gets the insert manager of the current table
     * @return the insert manager
     */
    public InsertManager insert() {
        return connection.insertTo(tableName());
    }

    /**
     * Adds a table field to the table
     *
     * @param tableField The table field you want to add
     */
    protected void custom(TableField tableField) {
        tableFields.add(tableField);
    }

    /**
     * Generates the table sql
     *
     * @return the table sql
     */
    public String generateSQL() {
        tableFields = new ArrayList<>();
        StringBuilder query = new StringBuilder();

        // Add table fields
        integer("id", 255, false, "", "AUTO_INCREMENT");
        tableFields();

        // Create the query
        query.append("CREATE TABLE IF NOT EXISTS ").append(tableName()).append("(");
        for (TableField tableField : tableFields)
            query.append(tableField.generateSQLRow()).append(", ");
        query.append("PRIMARY KEY (id)");
        query.append(") ENGINE=InnoDB;");

        // Return the query
        return query.toString();
    }

}
