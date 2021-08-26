package de.gnmyt.sqltoolkit.queries;

import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;

import java.util.HashMap;

import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.TABLE_NAME;
import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.WHERE_LIST;

public class DeletionQuery extends AbstractQuery {

    @Override
    public void defaults() {
        addParameter(WHERE_LIST, new HashMap<Object, String>());
        addParameter(TABLE_NAME, "default");
    }

    /**
     * Builds the 'where'-list
     *
     * @return the 'where'-list as a string
     */
    public String buildWhereList() {
        StatementBuilder whereString = new StatementBuilder();

        HashMap<String, Object> whereList = (HashMap<String, Object>) getParameter(WHERE_LIST);

        for (int i = 0; i < whereList.size(); i++) {
            if (i == 0) whereString.append("WHERE");
            else whereString.append("AND");

            whereString.append("`"+whereList.keySet().toArray()[i]+"`").append("=").append("?");
        }

        return whereString.toString();
    }

    /**
     * Gets the parameters
     * @return the parameters
     */
    public Object[] getParameters() {

        HashMap<String, Object> whereList = (HashMap<String, Object>) getParameter(WHERE_LIST);

        return whereList.values().toArray();
    }

    @Override
    public SQLQuery build() {

        StatementBuilder builder = new StatementBuilder("DELETE").append("FROM").append((String) getParameter(TABLE_NAME));

        builder.append(buildWhereList());

        return new SQLQuery().setStatement(builder.toString()).setParameters(getParameters());
    }
}
