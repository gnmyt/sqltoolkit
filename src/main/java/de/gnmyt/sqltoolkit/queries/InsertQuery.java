package de.gnmyt.sqltoolkit.queries;

import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;

import java.util.HashMap;

import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.TABLE_NAME;
import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.VALUE_LIST;

public class InsertQuery extends AbstractQuery {

    @Override
    public void defaults() {
        addParameter(TABLE_NAME, "default");
        addParameter(VALUE_LIST, new HashMap<String, Object>());
    }

    /**
     * Builds the rows of the 'values'-list
     *
     * @return the rows of the 'values'-list as a string
     */
    public String buildRowList() {
        StatementBuilder rowString = new StatementBuilder("(");

        HashMap<String, Object> valueList = (HashMap<String, Object>) getParameter(VALUE_LIST);

        for (int i = 0; i < valueList.size(); i++) {
            if (i > 0) rowString.appendDefault(",");
            rowString.append("`").appendDefault(valueList.keySet().toArray()[i].toString()).appendDefault("`");
        }

        return rowString.append(")").build();
    }

    /**
     * Builds the 'values'-list
     *
     * @return the 'values'-list as a string
     */
    public String buildValueList() {
        StatementBuilder valueString = new StatementBuilder();

        HashMap<String, Object> valueList = (HashMap<String, Object>) getParameter(VALUE_LIST);

        valueString.append(valueList.size() > 0 ? "VALUES (" : "");

        for (int i = 0; i < valueList.size(); i++) {
            if (i > 0) valueString.appendDefault(",");
            valueString.append("?");
        }

        if (valueList.size() > 0) valueString.append(")");

        return valueString.toString();
    }

    /**
     * Gets the parameters
     *
     * @return the parameters
     */
    public Object[] getParameters() {

        HashMap<String, Object> valueList = (HashMap<String, Object>) getParameter(VALUE_LIST);

        return valueList.values().toArray();
    }

    @Override
    public SQLQuery build() {
        StatementBuilder builder = new StatementBuilder("INSERT INTO").append("`" + getParameter(TABLE_NAME) + "`");

        builder.append(buildRowList());

        builder.append(buildValueList());

        return new SQLQuery().setStatement(builder.build()).setParameters(getParameters());
    }
}
