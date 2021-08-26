package de.gnmyt.sqltoolkit.queries;

import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.*;

public class UpdateQuery extends AbstractQuery {

    @Override
    public void defaults() {
        addParameter(TABLE_NAME, "default");
        addParameter(WHERE_LIST, new HashMap<String, Object>());
        addParameter(SET_LIST, new HashMap<String, Object>());
    }

    /**
     * Builds the 'set'-list
     *
     * @return the 'set'-list as a string
     */
    public String buildSetList() {
        StatementBuilder builder = new StatementBuilder("SET");

        HashMap<String, Object> setList = (HashMap<String, Object>) getParameter(SET_LIST);

        for (int i = 0; i < setList.size(); i++) {
            if (i > 0) builder.appendDefault(",");
            builder.append(setList.keySet().toArray()[i].toString()).append("=").append("?");
        }

        return builder.build();
    }

    /**
     * Builds the 'where'-list
     *
     * @return the 'where'-list as a string
     */
    public String buildWhereList() {
        StatementBuilder builder = new StatementBuilder("WHERE");

        HashMap<String, Object> whereList = (HashMap<String, Object>) getParameter(WHERE_LIST);

        for (int i = 0; i < whereList.size(); i++) {
            if (i > 0) builder.append("AND");
            builder.append(whereList.keySet().toArray()[i].toString()).append("=").append("?");
        }

        return builder.build();
    }

    /**
     * Gets the parameters
     *
     * @return the parameters
     */
    public Object[] getParameters() {

        ArrayList<Object> tempParameters = new ArrayList<>();

        ((HashMap<String, Object>) getParameter(SET_LIST)).forEach((str, obj) -> tempParameters.add(obj));
        ((HashMap<String, Object>) getParameter(WHERE_LIST)).forEach((str, obj) -> tempParameters.add(obj));

        return tempParameters.toArray();
    }

    @Override
    public SQLQuery build() {
        StatementBuilder builder = new StatementBuilder("UPDATE").append((String) getParameter(TABLE_NAME));

        if (!((HashMap<String, Object>) getParameter(SET_LIST)).isEmpty()) builder.append(buildSetList());

        if (!((HashMap<String, Object>) getParameter(WHERE_LIST)).isEmpty()) builder.append(buildWhereList());

        return new SQLQuery().setStatement(builder.build()).setParameters(getParameters());
    }
}
