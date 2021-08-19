package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.MySQLConnection;
import org.slf4j.Logger;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultManager {

    private final Logger LOG = MySQLConnection.LOG;

    private final ResultSet resultSet;

    /**
     * Basic constructor for the ResultManager
     *
     * @param resultSet Existing ResultSet
     */
    public ResultManager(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Getting the ResultSet
     *
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Get the Object of an Result
     *
     * @param column Table column
     * @return Object
     */
    public Object getObject(String column) {
        try {
            while (resultSet.next()) {
                return resultSet.getObject(column);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return null;
    }

    /**
     * Get the String of an Result
     *
     * @param column Table column
     * @return String
     */
    public String getString(String column) {
        try {
            while (resultSet.next()) {
                return resultSet.getString(column);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return "";
    }

    /**
     * Get the Integer of an Result
     *
     * @param column Table column
     * @return Integer
     */
    public Integer getInteger(String column) {
        try {
            while (resultSet.next()) {
                return resultSet.getInt(column);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return 0;
    }

    /**
     * Get the Boolean of an Result
     *
     * @param column Table column
     * @return Boolean
     */
    public Boolean getBoolean(String column) {
        try {
            while (resultSet.next()) {
                return resultSet.getBoolean(column);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return false;
    }

    public Timestamp getTimestamp(String column) {
        try {
            while (resultSet.next()) {
                return resultSet.getTimestamp(column);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return null;
    }

    /**
     * Get the count of the Result
     *
     * @return Integer (Count)
     */
    public Integer getRowCount() {
        int count = 0;
        try {
            while (resultSet.next()) {
                count++;
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return count;
    }

    /**
     * Checks if the current result exists
     * @return <code>true</code> if the current result exists, otherwise <code>false</code>
     */
    public boolean exists() {
        return getRowCount() != 0;
    }

    /**
     * Get a List of all Results
     *
     * @param column Table column
     * @return ArrayList of Result
     */
    public ArrayList<String> getList(String column) {
        ArrayList<String> results = new ArrayList<>();
        try {
            while (resultSet.next()) {
                results.add(resultSet.getString(column));
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return results;
    }

    /**
     * Get a List of all Results for 2 columns
     *
     * @param column  Table column #1
     * @param column2 Table column #2
     * @return HashMap of Result
     */
    public HashMap<String, String> getMultipleList(String column, String column2) {
        HashMap<String, String> results = new HashMap<>();
        try {
            while (resultSet.next()) {
                results.put(resultSet.getString(column), resultSet.getString(column2));
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return results;
    }

    /**
     * Get a list of all results with all columns
     *
     * @return ArrayList with the result
     */
    public ArrayList<HashMap<String, Object>> getList() {
        ArrayList<HashMap<String, Object>> results = new ArrayList<>();
        try {
            while (resultSet.next()) {
                HashMap<String, Object> result = new HashMap<>();
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i + 1);
                    result.put(columnName, resultSet.getObject(columnName));
                }
                results.add(result);
            }
        } catch (Exception err) {
            LOG.error(err.getMessage());
        }
        return results;
    }

}
