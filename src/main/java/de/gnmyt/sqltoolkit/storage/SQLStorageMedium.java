package de.gnmyt.sqltoolkit.storage;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SQLStorageMedium extends SQLTable {

    /**
     * The basic constructor of the {@link SQLStorageMedium}
     *
     * @param connection The mysql connection you want to use
     */
    public SQLStorageMedium(MySQLConnection connection) {
        super(connection);
    }

    @Override
    protected void tableFields() {
        string("keyName", 255, "");
        string("value", 2000, "");
    }

    /**
     * Checks if a key exists in the storage medium
     *
     * @param key The key you want to check
     * @return <code>true</code> if the key exists, otherwise <code>false</code>
     */
    public boolean exists(String key) {
        return select().where("keyName", key).getResult().exists();
    }

    /**
     * Inserts a key into the storage medium
     *
     * @param key   The key you want to insert
     * @param value The value you want to insert
     */
    public void insert(String key, String value) {
        insert().value("keyName", key).value("value", value).execute();
    }

    /**
     * Updates a key in the storage medium
     *
     * @param key   The key you want to use
     * @param value The value you want to update
     */
    public void update(String key, String value) {
        update().where("keyName", key).set("value", value).execute();
    }

    /**
     * Deletes a key from the storage medium
     *
     * @param key The key you want to delete
     */
    public void delete(String key) {
        delete().where("keyName", key).execute();
    }

    /**
     * Gets the value of a key
     *
     * @param key The key of the value you want to get
     * @return the value you want to get
     */
    public String get(String key) {
        return select().where("keyName", key).getResult().getString("value");
    }

    /**
     * Gets all entries from the storage medium
     *
     * @return the entries from the storage medium
     */
    public ArrayList<HashMap<String, Object>> getEntries() {
        return select().getResult().getList();
    }

    /**
     * Inserts or updates the key of the storage medium.
     * If the entry is currently in the storage medium, it updates it. Otherwise, it will insert it
     *
     * @param key   The key you want to insert / update
     * @param value The value you want to insert / update
     */
    public void insertOrUpdate(String key, String value) {
        if (exists(key)) {
            update(key, value);
        } else {
            insert(key, value);
        }
    }

}
