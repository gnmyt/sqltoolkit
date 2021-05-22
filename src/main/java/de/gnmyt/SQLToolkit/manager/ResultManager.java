package de.gnmyt.SQLToolkit.manager;

import de.gnmyt.SQLToolkit.drivers.SqlLogManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public class ResultManager {

    private ResultSet resultSet;
    private SqlLogManager logManager;

    /**
     * Basic constructor for the ResultManager
     * @param resultSet Existing ResultSet
     * @param logManager Existing LogManager
     */
    public ResultManager(ResultSet resultSet, SqlLogManager logManager) {
        this.resultSet = resultSet;
        this.logManager = logManager;
    }

    /**
     * Getting the ResultSet
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Get the Object of an Result
     * @param column Table column
     * @return Object
     */
    public Object getObject(String column) {
        try { while (resultSet.next()) { return resultSet.getObject(column); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return null;
    }

    /**
     * Get the String of an Result
     * @param column Table column
     * @return String
     */
    public String getString(String column) {
        try { while (resultSet.next()) { return resultSet.getString(column); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return "";
    }

    /**
     * Get the Integer of an Result
     * @param column Table column
     * @return Integer
     */
    public Integer getInteger(String column) {
        try { while (resultSet.next()) { return resultSet.getInt(column); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return 0;
    }

    /**
     * Get the Boolean of an Result
     * @param column Table column
     * @return Boolean
     */
    public Boolean getBoolean(String column) {
        try { while (resultSet.next()) { return resultSet.getBoolean(column); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return false;
    }

    /**
     * Get the count of the Result
     * @return Integer (Count)
     */
    public Integer getRowCount() {
        int count=0;
        try { while (resultSet.next()) { count++; } } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return count;
    }

    /**
     * Get a List of all Results
     * @param column Table column
     * @return ArrayList of Result
     */
    public ArrayList<String> getList(String column) {
        ArrayList<String> results = new ArrayList<>();
        try { while (resultSet.next()) { results.add(resultSet.getString(column)); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return results;
    }

    /**
     * Get a List of all Results for 2 columns
     * @param column Table column #1
     * @param column2 Table column #2
     * @return HashMap of Result
     */
    public HashMap<String, String> getMultipleList(String column, String column2) {
        HashMap<String, String> results = new HashMap<>();
        try { while (resultSet.next()) {
            results.put(resultSet.getString(column), resultSet.getString(column2)); }
        } catch (Exception err) { logManager.sendError(err.getMessage()); }
        return results;
    }

}
