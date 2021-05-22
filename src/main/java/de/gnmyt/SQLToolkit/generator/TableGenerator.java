package de.gnmyt.SQLToolkit.generator;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import de.gnmyt.SQLToolkit.fields.SQLField;
import de.gnmyt.SQLToolkit.manager.UpdateManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public class TableGenerator {

    private UpdateManager updateManager;
    private ArrayList<String> fields;
    private boolean useID;
    private String tableName;
    MySQLConnection connection;

    /**
     * Basic constructor for the TableGenerator
     * @param updateManager Existing update de.gnmyt.SQLToolkit.manager
     * @param tableName Name of the table
     */
    public TableGenerator(UpdateManager updateManager, String tableName) {
        this.updateManager = updateManager;
        this.tableName = tableName;
        this.fields = new ArrayList<>();
        connection = updateManager.getConnection();
    }

    /**
     * Add an 'id' field to the Table
     * @return this class
     */
    public TableGenerator useID() {
        this.useID = true;
        return this;
    }

    /**
     * Add a field to the Table
     * @param field String of the field
     * @return this class
     */
    public TableGenerator addField(String field) {
        fields.add(field);
        return this;
    }

    /**
     * Add a field to the Table
     * @param field Existing SQL Field
     * @return this class
     */
    public TableGenerator addField(SQLField field) {
        String temp = "";
        temp += "`" + field.getName() + "` " + field.getSqlType().getValue() + "(" + field.getLength() + ")";
        temp += !field.getDefaultValue().isEmpty() ? " DEFAULT '" + field.getDefaultValue() + "'" : "";
        temp += !field.getOptionalParameter().isEmpty() ? " " + field.getOptionalParameter() : "";
        fields.add(temp);
        return this;
    }

    /**
     * Create the table you wanted
     * @return this class
     */
    public TableGenerator create() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS " + tableName + " ( ");
        AtomicBoolean used = new AtomicBoolean(false);
        if (useID) sb.append(used.get() ? ", " : "").append("`id` INT(255) NOT NULL AUTO_INCREMENT");used.set(true);
        fields.forEach(string -> { sb.append(used.get() ? ", " : "").append(string);used.set(true); });
        if (useID) sb.append(used.get() ? ", " : "").append("PRIMARY KEY (`id`)");used.set(true);
        sb.append(" ) ENGINE = InnoDB;");
        connection.update(sb.toString());
        return this;
    }

}
