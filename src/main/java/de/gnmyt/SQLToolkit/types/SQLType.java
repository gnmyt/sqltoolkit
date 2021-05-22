package de.gnmyt.SQLToolkit.types;

public enum SQLType {

    STRING("VARCHAR"),
    INTEGER("INT"),
    DATE("DATE"),
    DATETIME("DATETIME");

    private String value = "";

    /**
     * Basic constructor for the SQLType enum
     * @param value MySQL data type
     */
    SQLType(String value) {
        this.value = value;
    }

    /**
     * Get the value of the chosen enum
     * @return
     */
    public String getValue() {
        return value;
    }

}
