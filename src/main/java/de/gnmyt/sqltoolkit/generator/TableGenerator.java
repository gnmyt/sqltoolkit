package de.gnmyt.sqltoolkit.generator;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.queries.TableCreationQuery;
import de.gnmyt.sqltoolkit.querybuilder.QueryParameter;
import de.gnmyt.sqltoolkit.querybuilder.SQLQuery;
import de.gnmyt.sqltoolkit.types.SQLType;
import de.gnmyt.sqltoolkit.types.TableField;

import java.util.ArrayList;

public class TableGenerator {

    private final MySQLConnection connection;

    private final ArrayList<TableField> fields = new ArrayList<>();
    private final String tableName;

    /**
     * Basic constructor for the {@link TableGenerator}
     *
     * @param tableName Name of the table
     */
    public TableGenerator(MySQLConnection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
        addField(SQLType.INTEGER, "id", 255, null, "AUTO_INCREMENT");
    }

    /**
     * Add a field to the Table
     *
     * @param type               The type of the field you want to add
     * @param name               The name of the field you want to add
     * @param length             The length of the field you want to add
     * @param defaultValue       The default value of the field (leave empty for no default value)
     * @param extras Optional parameters you want to add to the statement
     * @return this class
     */
    public TableGenerator addField(SQLType type, String name, Integer length, String defaultValue, String... extras) {
        fields.add(new TableField().setType(type).setName(name).setLength(length).setDefaultValue(defaultValue).setExtras(extras));
        return this;
    }

    /**
     * Add a field to the Table
     *
     * @param type   The type of the field you want to add
     * @param name   The name of the field you want to add
     * @param length The length of the field you want to add
     * @return this class
     */
    public TableGenerator addField(SQLType type, String name, Integer length) {
        return addField(type, name, length, "");
    }

    /**
     * Add a field to the table
     * @param field The field you want to add
     * @return this class
     */
    public TableGenerator addField(TableField field) {
        fields.add(field);
        return this;
    }

    /**
     * Add a field to the Table
     *
     * @param type The type of the field you want to add
     * @param name The name of the field you want to add
     * @return this class
     */
    public TableGenerator addField(SQLType type, String name) {
        return addField(type, name, 255, "");
    }

    /**
     * Creates the table you wanted
     */
    public void create() {
        SQLQuery query = connection.createQuery(TableCreationQuery.class)
                .addParameter(QueryParameter.TABLE_NAME, tableName)
                .addParameter(QueryParameter.FIELD_LIST, fields)
                .addParameter(QueryParameter.PRIMARY_KEY, "id")
                .build();
        connection.update(query);
    }

}
