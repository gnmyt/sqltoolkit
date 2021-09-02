package de.gnmyt.sqltoolkit.storage;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.manager.*;
import de.gnmyt.sqltoolkit.queries.TableCreationQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.types.SQLType;
import de.gnmyt.sqltoolkit.types.TableField;

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
     * @param name   The name of the boolean you want to add
     * @param extras The extras you want to add to the boolean
     */
    protected void bool(String name, String... extras) {
        custom(SQLType.BOOLEAN.getValue(), name, 1, false, "", extras);
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
        custom(new TableField(name, type, length, allowNull, defaultValue).setExtras(extras));
    }

    /**
     * Adds a table field to the table
     *
     * @param tableField The table field you want to add
     */
    public void custom(TableField tableField) {
        tableFields.add(tableField);
    }

    /**
     * Adds a custom table field to the table
     *
     * @param name The name of the column
     * @return the manager of the table field
     */
    protected CustomTableFieldManager custom(String name) {
        return new CustomTableFieldManager(this).name(name);
    }

    /**
     * Adds a custom table field to the table
     *
     * @return the manager of the table field
     */
    protected CustomTableFieldManager custom() {
        return new CustomTableFieldManager(this);
    }

    /**
     * Gets the database selection from the current the table
     *
     * @return the database selection
     */
    public SelectionManager select() {
        return connection.selectFrom(tableName());
    }

    /**
     * Gets the update manager of the current table
     *
     * @return the update manager
     */
    public UpdateManager update() {
        return connection.updateTo(tableName());
    }

    /**
     * Gets the insert manager of the current table
     *
     * @return the insert manager
     */
    public InsertManager insert() {
        return connection.insertTo(tableName());
    }

    /**
     * Gets the deletion manager of the current table
     *
     * @return the deletion manager
     */
    public DeletionManager delete() {
        return connection.deleteFrom(tableName());
    }

    /**
     * Creates the table
     */
    public void create() {
        tableFields = new ArrayList<>();

        // Add table fields
        integer("id", 255, false, "", "AUTO_INCREMENT");
        tableFields();

        SQLQuery query = connection.createQuery(TableCreationQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName())
                .addParameter(QueryParameter.PRIMARY_KEY, "id")
                .addParameter(QueryParameter.FIELD_LIST, tableFields)
                .build();

        connection.update(query);
    }

}
