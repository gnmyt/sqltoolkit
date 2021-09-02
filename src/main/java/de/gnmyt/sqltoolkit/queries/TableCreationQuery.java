package de.gnmyt.sqltoolkit.queries;

import de.gnmyt.sqltoolkit.querybuilder.AbstractQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.querybuilder.StatementBuilder;
import de.gnmyt.sqltoolkit.types.TableField;

import java.util.ArrayList;

import static de.gnmyt.sqltoolkit.querybuilder.QueryParameter.PRIMARY_KEY;

public class TableCreationQuery extends AbstractQuery {

    @Override
    public void defaults() {
        addParameter(QueryParameter.TABLE_NAME, "default");
        addParameter(QueryParameter.FIELD_LIST, new ArrayList<TableField>());
        addParameter(PRIMARY_KEY, "");
    }

    /**
     * Builds the field list
     *
     * @return the field list as a string
     */
    public String buildFieldList() {
        StatementBuilder builder = new StatementBuilder("(");

        ArrayList<TableField> fieldList = (ArrayList<TableField>) getParameter(QueryParameter.FIELD_LIST);

        for (int i = 0; i < fieldList.size(); i++) {
            if (i > 0) builder.appendDefault(",");
            builder.append(fieldList.get(i).generateSQLRow());
        }

        if (!((String) getParameter(PRIMARY_KEY)).isEmpty())
            builder.appendDefault(String.format(", PRIMARY KEY (%s)", getParameter(PRIMARY_KEY)));

        return builder.append(")").build();
    }

    @Override
    public SQLQuery build() {
        StatementBuilder builder = new StatementBuilder("CREATE TABLE IF NOT EXISTS").append("`" + getParameter(QueryParameter.TABLE_NAME) + "`");

        builder.append(buildFieldList());

        return new SQLQuery().setStatement(builder.build());
    }
}
