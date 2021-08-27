package de.gnmyt.sqltoolkit.factory;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.storage.SQLStorageMedium;
import de.gnmyt.sqltoolkit.storage.SQLTable;

import java.util.HashMap;

public class TableFactory {

    private final HashMap<Class<? extends SQLTable>, SQLTable> REGISTERED_TABLES = new HashMap<>();

    private final MySQLConnection connection;

    /**
     * Basic constructor of the {@link TableFactory}
     *
     * @param connection The mysql connection
     */
    public TableFactory(MySQLConnection connection) {
        this.connection = connection;
    }

    /**
     * Registers and creates a sql table
     *
     * @param table The table you want to register
     * @return this class
     */
    public TableFactory register(SQLTable table) {
        table.create();
        REGISTERED_TABLES.put(table.getClass(), table);
        return this;
    }

    /**
     * Gets a registered table from the list
     *
     * @param tableClass The class of the table you want to get
     * @return the instance of the table
     */
    public SQLTable getTable(Class<? extends SQLTable> tableClass) {
        return REGISTERED_TABLES.get(tableClass);
    }

    /**
     * Gets a storage medium from the list
     *
     * @param storageClass The class of the storage medium you want to get
     * @return the storage medium
     */
    public SQLStorageMedium getStorage(Class<? extends SQLStorageMedium> storageClass) {
        return (SQLStorageMedium) getTable(storageClass);
    }

}
