package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.storage.SQLTable;
import de.gnmyt.SQLToolkit.types.SQLType;
import de.gnmyt.SQLToolkit.types.TableField;

public class CustomTableFieldManager {

    private final SQLTable table;

    private final TableField field = new TableField();

    /**
     * Basic constructor of the {@link CustomTableFieldManager}
     *
     * @param table Your current sql table
     */
    public CustomTableFieldManager(SQLTable table) {
        this.table = table;
    }

    /**
     * Set the name of the table field
     *
     * @param name The new name of the table field
     * @return this class
     */
    public CustomTableFieldManager name(String name) {
        field.setName(name);
        return this;
    }

    /**
     * Sets the type of the table field
     *
     * @param type The new type of the table field
     * @return this class
     */
    public CustomTableFieldManager type(SQLType type) {
        field.setType(type.getValue());
        return this;
    }

    /**
     * Sets the type of the table field as a string
     *
     * @param type The new type of the table field
     * @return this class
     */
    public CustomTableFieldManager type(String type) {
        field.setType(type);
        return this;
    }

    /**
     * Sets the length of the table field
     *
     * @param length The new length of the table field
     * @return this class
     */
    public CustomTableFieldManager length(int length) {
        field.setLength(length);
        return this;
    }

    /**
     * Allows a null in the table field
     *
     * @param allowNull <code>true</code> if a <b>NULL</b> is allowed, otherwise <code>false</code>
     * @return this class
     */
    public CustomTableFieldManager allowNull(boolean allowNull) {
        field.setAllowNull(allowNull);
        return this;
    }

    /**
     * Sets the default value of the table field
     *
     * @param defaultValue The new default value of the table field
     * @return this class
     */
    public CustomTableFieldManager defaultValue(String defaultValue) {
        field.setDefaultValue(defaultValue);
        return this;
    }

    /**
     * Sets the extras of the table field
     *
     * @param extras The new extras of the table field
     * @return this class
     */
    public CustomTableFieldManager extras(String[] extras) {
        field.setExtra(extras);
        return this;
    }

    /**
     * Adds the current field to the sql table
     */
    public void add() {
        table.custom(field);
    }

}
