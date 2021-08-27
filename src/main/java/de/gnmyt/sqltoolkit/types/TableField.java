package de.gnmyt.sqltoolkit.types;

import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;

public class TableField {

    private String name = "default";
    private String type = SQLType.STRING.getValue();
    private boolean allowNull = false;
    private String[] extras = new String[0];
    private int length = 0;
    private Object defaultValue;

    /**
     * Basic constructor of the {@link TableField}
     *
     * @param name         The name of the field
     * @param type         The type of the field
     * @param length       The length of the field
     * @param allowNull    <code>true</code> if you want to allow a <b>NULL</b> in the field, otherwise <code>false</code>
     * @param defaultValue The default value of the field
     */
    public TableField(String name, String type, int length, boolean allowNull, Object defaultValue) {
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
        return length;
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
    public String[] getExtras() {
        return extras;
    }


    /**
     * Sets the extras of the field
     * @param extras The extras you want to add
     * @return this class
     */
    public TableField setExtras(String... extras) {
        this.extras = extras;
        return this;
    }

    /**
     * Gets the extras of the field as a string
     *
     * @return the extras of the field as a string
     */
    public String getExtraString() {
        StatementBuilder extras = new StatementBuilder();
        for (int i = 0; i < getExtras().length; i++)
            extras.append(getExtras()[i]);
        return extras.build();
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
        if (defaultValue == null) return "";
        if (defaultValue instanceof String && (((String) defaultValue).isEmpty())) return "";

        return "DEFAULT " + (defaultValue instanceof String ? "'" + defaultValue + "'" : defaultValue);
    }

    /**
     * Sets the default value of the field
     *
     * @param defaultValue The new default value of the field
     * @return this class
     */
    public TableField setDefaultValue(Object defaultValue) {
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
        return String.format("`%s` %s%s%s%s%s", getName(), getType(),
                getLength() == 0 ? "" : "(" + getLength() + ")",
                getNullAsSQL().isEmpty() ? "" : " " + getNullAsSQL(),
                getDefaultValue().isEmpty() ? "" : " " + getDefaultValue(),
                getExtraString().isEmpty() ? "" : " " + getExtraString());
    }


}
