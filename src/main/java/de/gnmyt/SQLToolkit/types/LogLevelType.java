package de.gnmyt.SQLToolkit.types;

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
