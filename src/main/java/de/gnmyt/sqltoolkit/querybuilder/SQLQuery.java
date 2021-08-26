package de.gnmyt.sqltoolkit.querybuilder;

import java.util.Arrays;

public class SQLQuery {

    private String statement = "";
    private String[] parameters = new String[0];

    /**
     * Advanced constructor of the {@link SQLQuery} with prefilled values
     *
     * @param statement  The statement of the query
     * @param parameters The query parameters
     */
    public SQLQuery(String statement, String[] parameters) {
        this.statement = statement;
        this.parameters = parameters;
    }

    /**
     * Basic constructor of the {@link SQLQuery}
     */
    public SQLQuery() {

    }

    /**
     * Sets the new statement of the query
     *
     * @param statement The new query statement
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * Sets the new parameters of the query
     *
     * @param parameters The new query parameters
     */
    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    /**
     * Gets the current statement of the query
     *
     * @return the current statement
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Gets the current query parameters
     *
     * @return the current query parameters
     */
    public String[] getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "SQLQuery{" +
                "statement='" + statement + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
