package de.gnmyt.SQLToolkit.fields;

import de.gnmyt.SQLToolkit.types.SQLType;

public class SQLField {

    private String name = "";
    private String defaultValue = "";
    private String optionalParameter = "";
    private Integer length = 0;
    private SQLType sqlType;

    /**
     * Basic constructor for the SQL field
     * @param sqlType Type of the SQL field
     * @param name Name of the SQL field
     * @param length Length of the SQL field
     */
    public SQLField(SQLType sqlType, String name, Integer length) {
        this.name = name;
        this.sqlType = sqlType;
        this.length = length;
    }

    /**
     * Set the default value for the SQL field
     * @param defaultValue New default value
     * @return this class
     */
    public SQLField setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * Set optional parameter for the SQL field
     * @param optionalParameter New optional parameter
     * @return this class
     */
    public SQLField setOptionalParameter(String optionalParameter) {
        this.optionalParameter = optionalParameter;
        return this;
    }

    /**
     * Set the length of the SQL field
     * @param length New length
     * @return this class
     */
    public SQLField setLength(Integer length) {
        this.length = length;
        return this;
    }

    /**
     * Set the name of the SQL field
     * @param name New name
     * @return this class
     */
    public SQLField setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the length of the SQL Field
     * @return length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Get the default value of the SQL Field
     * @return default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Get the optional parameters of the SQL Field
     * @return this class
     */
    public String getOptionalParameter() {
        return optionalParameter;
    }

    /**
     * Get the name of the SQL Field
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the SQL Type of the SQL Field
     * @return SQL Type
     */
    public SQLType getSqlType() {
        return sqlType;
    }

}
