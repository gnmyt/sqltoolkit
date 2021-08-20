package de.gnmyt.sqltoolkit.types;

public class TableField {

    private String name = "default";
    private String type = SQLType.STRING.getValue();
    private int length = 255;
    private boolean allowNull = false;
    private String defaultValue = "";
    private String[] extra = new String[0];

    /**
     * Basic constructor of the {@link TableField}
     *
     * @param name         The name of the field
     * @param type         The type of the field
     * @param length       The length of the field
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the field
     */
    public TableField(String name, String type, int length, boolean allowNull, String defaultValue) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.allowNull = allowNull;
        this.defaultValue = defaultValue;
    }

    /**
     * Simple constructor of the {@link TableField}
     *
     * @param name The name of the field
     */
    public TableField(String name) {
        this.name = name;
    }

    /**
     * Simple constructor with no prefilled variables
     */
    public TableField() {

    }

    /**
     * Gets the name of the field
     *
     * @return the name of the field
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the field
     *
     * @param name The new name of the field
     * @return this class
     */
    public TableField setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the type of the field
     *
     * @return the type of the field
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the field
     *
     * @param type The new type of the field
     * @return this class
     */
    public TableField setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the type of the field (with a sql type)
     *
     * @param type The new type of the field
     * @return this class
     */
    public TableField setType(SQLType type) {
        this.type = type.getValue();
        return this;
    }

    /**
     * Gets the length of the field
     *
     * @return the length of the field
     */
    public int getLength() {
        return length != 0 ? length : 255;
    }

    /**
     * Sets the length of the field
     *
     * @param length The new length of the field
     * @return this class
     */
    public TableField setLength(int length) {
        this.length = length;
        return this;
    }

    /**
     * Gets the extra of the field
     *
     * @return the extra of the field
     */
    public String[] getExtra() {
        return extra;
    }

    /**
     * Sets the extras of the field
     *
     * @param extra The new extras of the field
     * @return this class
     */
    public TableField setExtra(String[] extra) {
        this.extra = extra;
        return this;
    }

    /**
     * Gets the extras of the field as a string
     *
     * @return the extras of the field as a string
     */
    public String getExtras() {
        StringBuilder extras = new StringBuilder();
        for (int i = 0; i < getExtra().length; i++)
            extras.append(getExtra()[i]);
        return extras.toString();
    }

    /**
     * Gets the allowNull variable as sql string
     *
     * @return the allowNull variable as sql string
     */
    public String getNullAsSQL() {
        return !isAllowNull() ? "NOT NULL" : "";
    }

    /**
     * Gets the default value of the field
     *
     * @return the default value of the field
     */
    public String getDefaultValue() {
        return !defaultValue.isEmpty() ? "DEFAULT '" + defaultValue + "'" : "";
    }

    /**
     * Sets the default value of the field
     *
     * @param defaultValue The new default value of the field
     * @return this class
     */
    public TableField setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * Checks if the field is allowed to have a <b>NULL</b>
     *
     * @return <code>true</code> if the field is <b>NULL</b>, otherwise <code>false</code>
     */
    public boolean isAllowNull() {
        return allowNull;
    }

    /**
     * Sets the allowNull variable of the field
     *
     * @param allowNull <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @return this class
     */
    public TableField setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
        return this;
    }

    /**
     * Gets the generated sql row
     *
     * @return the generated sql row
     */
    public String generateSQLRow() {
        return String.format("`%s` %s(%d) %s %s %s", getName(), getType(), getLength(), getNullAsSQL(), getDefaultValue(), getExtras());
    }


}
