package de.gnmyt.sqltoolkit.types;

public enum SQLType {

    VARCHAR("VARCHAR"),
    STRING("TEXT"),
    INTEGER("INT"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME"),
    YEAR("YEAR"),
    BOOLEAN("TINYINT"),
    BIG_INTEGER("BIGINT"),
    FLOAT("FLOAT");

    private final String value;

    /**
     * Basic constructor of the {@link SQLType} enum
     *
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
