package de.gnmyt.SQLToolkit.generator;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import de.gnmyt.SQLToolkit.types.SQLType;
import de.gnmyt.SQLToolkit.types.TableField;

import java.util.ArrayList;

public class TableGenerator {

    private final MySQLConnection connection;

    private final ArrayList<String> fields;
    private final String tableName;

    /**
     * Basic constructor for the {@link TableGenerator}
     *
     * @param tableName Name of the table
     */
    public TableGenerator(MySQLConnection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
        this.fields = new ArrayList<>();
    }

    /**
     * Add a field to the Table
     *
     * @param field String of the field
     * @return this class
     */
    public TableGenerator addField(String field) {
        fields.add(field);
        return this;
    }

    /**
     * Add a field to the Table
     *
     * @param type               The type of the field you want to add
     * @param name               The name of the field you want to add
     * @param length             The length of the field you want to add
     * @param defaultValue       The default value of the field (leave empty for no default value)
     * @param optionalParameters Optional parameters you want to add to the statement
     * @return this class
     */
    public TableGenerator addField(SQLType type, String name, Integer length, String defaultValue, String... optionalParameters) {
        StringBuilder temp = new StringBuilder();
        temp.append("`").append(name).append("` ").append(type.getValue()).append("(").append(length).append(")");
        temp.append(!defaultValue.isEmpty() ? " DEFAULT '" + defaultValue + "'" : "");

        for (String optionalParameter : optionalParameters) {
            temp.append(" ").append(optionalParameter);
        }

        fields.add(temp.toString());
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

    public TableGenerator addField(TableField field) {
        fields.add(field.generateSQLRow());
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
     *
     * @return this class
     */
    public TableGenerator create() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" ( ");

        sb.append("`id` INT(255) NOT NULL AUTO_INCREMENT");

        for (String field : fields) {
            sb.append(", ").append(field);
        }

        sb.append(", PRIMARY KEY (`id`)");

        sb.append(" ) ENGINE = InnoDB;");
        connection.update(sb.toString());
        return this;
    }

}
