package drivers;

import types.LogLevelType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/********************************
 * @author Mathias Wagner
 * Created 23.12.2020
 ********************************/

public class SqlLogManager {

    private LogLevelType logLevelType;
    private String logFormat = "[%date » MySQL %type] %msg";

    /**
     * Sets the Log Level
     * @param logLevelType Logging Level
     */
    public void setLogLevelType(LogLevelType logLevelType) {
        this.logLevelType = logLevelType;
    }

    /**
     * Format for the Message
     * @param logFormat The Formatted String
     */
    public void setLogFormat(String logFormat) {
        this.logFormat = logFormat;
    }

    /**
     * Getting the Logging Level
     * @return LogLevelType
     */
    public LogLevelType getLogLevelType() {
        return logLevelType;
    }

    /**
     * Send an information
     * @param msg The provided message
     */
    public void sendInfo(String msg) {
        if (logLevelType == LogLevelType.ALL)
            sendLog("Info", msg);
    }

    /**
     * Send an Warning
     * @param msg The provided message
     */
    public void sendWarning(String msg) {
        if (logLevelType == LogLevelType.ALL || logLevelType == LogLevelType.LOW)
            sendLog("Warning", msg);
    }

    /**
     * Send an Error
     * @param msg The provided message
     */
    public void sendError(String msg) {
        if (logLevelType == LogLevelType.ALL || logLevelType == LogLevelType.LOW) {
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            StackTraceElement stack = st[st.length-1];
            sendLog("Error", "<"+stack.getFileName()+":"+stack.getLineNumber()+"> "+msg);
        }
    }

    /**
     * Intern Logging Feature
     * @param prefix Logging Prefix
     * @param msg The provided message
     */
    private void sendLog(String prefix, String msg) {
        String formatted = logFormat.replace("%type", prefix).replace("%date", getDate()).replace("%msg", msg);
        System.out.println(formatted);
    }

    /**
     * Getting the date
     * @return Datetime
     */
    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m:s");
        LocalDateTime dt = LocalDateTime.now();
        return dtf.format(dt);
    }

}
