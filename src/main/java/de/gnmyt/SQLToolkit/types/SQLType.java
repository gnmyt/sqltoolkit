package de.gnmyt.SQLToolkit.types;

public enum SQLType {

    VARCHAR("VARCHAR"),
    STRING("TEXT"),
    INTEGER("INT"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    BOOLEAN("TINYINT");

    private String value;

    /**
     * Basic constructor of the {@link SQLType} enum
     * @param value The value of the type
     */
    SQLType(String value) {
        this.value = value;
    }

    /**
     * Get the value of the chosen enum
     *
     * @return
     */
    public String getValue() {
        return value;
    }

}
