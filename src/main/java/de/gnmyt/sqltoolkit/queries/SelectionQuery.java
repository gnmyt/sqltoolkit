package de.gnmyt.sqltoolkit.queries;

import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.*;

public class SelectionQuery extends AbstractQuery {

    @Override
    public void defaults() {

        ArrayList<String> selectList = new ArrayList<>();
        selectList.add("*");

        addParameter(SELECT_LIST, selectList);
        addParameter(WHERE_LIST, new HashMap<Object, String>());
        addParameter(TABLE_NAME, "default");
    }

    /**
     * Builds the selection list
     *
     * @return the list as a string
     */
    public String buildSelectionList() {
        StringBuilder selectionString = new StringBuilder();

        for (String selectItem : ((ArrayList<String>) getParameter(SELECT_LIST))) {
            if (!selectionString.toString().isEmpty()) selectionString.append(", ");
            selectionString.append(selectItem);
        }

        return selectionString.toString();
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

        StatementBuilder builder = new StatementBuilder("SELECT").append(buildSelectionList()).append("FROM").append("`" + getParameter(TABLE_NAME) + "`");

        builder.append(buildWhereList());

        if (getParameter(LIMIT) != null) builder.append("LIMIT").append(getParameter(LIMIT).toString());

        return new SQLQuery().setStatement(builder.build()).setParameters(getParameters());
    }

}
