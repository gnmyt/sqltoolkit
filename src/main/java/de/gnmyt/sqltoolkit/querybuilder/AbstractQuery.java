package de.gnmyt.sqltoolkit.querybuilder;

import java.util.HashMap;

public abstract class AbstractQuery {

    private final HashMap<QueryParameter, Object> PARAMETERS = new HashMap<>();

    /**
     * Here the query adds its default values
     */
    public abstract void defaults();

    /**
     * The building logic of the sql query
     *
     * @return the sql query
     */
    public abstract SQLQuery build();

    /**
     * Adds a query parameter to the building list
     *
     * @param type  The type you want to use
     * @param value The value of the type
     */
    public void addParameter(QueryParameter type, Object value) {
        PARAMETERS.put(type, value);
    }

    /**
     * Gets a query parameter from the building list
     *
     * @param type The type you want to get
     * @return the query parameter
     */
    protected Object getParameter(QueryParameter type) {
        return PARAMETERS.get(type);
    }

}
