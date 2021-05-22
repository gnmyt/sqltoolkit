package de.gnmyt.SQLToolkit.types;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public enum LogLevelType {

    NONE("Sends nothing"),
    LOW("Sends Warnings & Errors"),
    ALL("Sends Infos, Warnings & Errors");

    /**
     * Basic constructor for the LogLevelType enum
     * @param info Info for documentation
     */
    LogLevelType(String info) { }

}
