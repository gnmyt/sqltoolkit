package de.gnmyt.SQLToolkit.factory;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import de.gnmyt.SQLToolkit.storage.SQLTable;

import java.util.HashMap;

public class TableFactory {

    private final HashMap<Class<? extends SQLTable>, SQLTable> REGISTERED_TABLES = new HashMap<>();

    private MySQLConnection connection;

    public TableFactory(MySQLConnection connection) {
        this.connection = connection;
    }

    /**
     * Registers and creates a sql table
     * @param table The table you want to register
     * @return this class
     */
    public TableFactory register(SQLTable table) {
        connection.update(table.generateSQL());
        REGISTERED_TABLES.put(table.getClass(), table);
        return this;
    }

    /**
     * Gets a registered table from the list
     * @param tableClass The class of the table you want to get
     * @return the instance of the table
     */
    public SQLTable getTable(Class<? extends SQLTable> tableClass) {
        return REGISTERED_TABLES.get(tableClass);
    }

}
