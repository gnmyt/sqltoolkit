package de.gnmyt.sqltoolkit.querybuilder;

/**
 * All usable query parameters for the {@link QueryBuilder}
 */
public enum QueryParameter {

    /**
     * The name of the table provided as a {@link String}
     */
    TABLE_NAME,

    /**
     * All 'where'-parameters of the query provided as a {@link java.util.HashMap}
     */
    WHERE_LIST,

    /**
     * All values of the query provided as a {@link java.util.HashMap}
     */
    VALUE_LIST,

    /**
     * All 'set'-parameters of the query provided as a {@link java.util.HashMap}
     */
    SET_LIST,

    /**
     * The selection list of the query provided as a {@link java.util.ArrayList}
     */
    SELECT_LIST,

    /**
     * The row limit of a query provided as a {@link String}
     */
    LIMIT


}
