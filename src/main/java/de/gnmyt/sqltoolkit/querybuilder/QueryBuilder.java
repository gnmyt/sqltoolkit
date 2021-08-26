package de.gnmyt.sqltoolkit.querybuilder;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import org.slf4j.Logger;

public class QueryBuilder {

    private static final Logger LOG = MySQLConnection.LOG;

    private AbstractQuery query;

    /**
     * Basic constructor of the {@link QueryBuilder}
     *
     * @param queryType The type of the query you want to generate
     */
    public QueryBuilder(Class<? extends AbstractQuery> queryType) {
        try {
            query = queryType.newInstance();
            query.defaults();
        } catch (Exception e) {
            LOG.error("Could not initialize query: " + e.getMessage());
        }
    }

    /**
     * Adds a parameter to the building list
     *
     * @param type  The type you want to use
     * @param value The value of the type
     * @return this class
     */
    public QueryBuilder addParameter(QueryParameter type, Object value) {
        query.addParameter(type, value);
        return this;
    }

    /**
     * Builds the query
     *
     * @return the built query
     */
    public SQLQuery build() {
        return query.build();
    }


}
