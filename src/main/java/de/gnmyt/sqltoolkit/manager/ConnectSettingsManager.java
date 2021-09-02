package de.gnmyt.sqltoolkit.manager;

import de.gnmyt.sqltoolkit.drivers.MySQLConnection;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ConnectSettingsManager {

    private final MySQLConnection connection;

    private final HashMap<String, Object> settingValues = new HashMap<>();

    public ConnectSettingsManager(MySQLConnection connection) {
        this.connection = connection;
    }

    /**
     * Adds a custom property to the values
     *
     * @param property The property you want to add
     * @param value    The value you want to use
     * @return this class
     */
    public ConnectSettingsManager customProperty(String property, Object value) {
        settingValues.put(property, value);
        return this;
    }


    /**
     * Sets the timezone of the connection
     *
     * @param timezone The new timezone
     * @return this class
     */
    public ConnectSettingsManager timezone(String timezone) {
        settingValues.put("useTimezone", true);
        settingValues.put("serverTimezone", timezone);
        return this;
    }

    /**
     * Sets the requireSSL property
     *
     * @param requireSSL The new value of the requireSSL property
     * @return this class
     */
    public ConnectSettingsManager requireSSL(boolean requireSSL) {
        settingValues.put("requireSSL", requireSSL);
        return this;
    }

    /**
     * Sets the useSSL property
     *
     * @param useSSL The new value of the useSSL property
     * @return this class
     */
    public ConnectSettingsManager useSSL(boolean useSSL) {
        settingValues.put("useSSL", useSSL);
        return this;
    }

    /**
     * Sets the autoReconnect property
     *
     * @param autoReconnect The new value of the autoReconnect property
     * @return this class
     */
    public ConnectSettingsManager autoReconnect(boolean autoReconnect) {
        settingValues.put("autoReconnect", autoReconnect);
        return this;
    }

    /**
     * Sets the maxReconnects property
     *
     * @param maxReconnects The new value of the maxReconnects property
     * @return this class
     */
    public ConnectSettingsManager maxReconnects(int maxReconnects) {
        settingValues.put("maxReconnects", maxReconnects);
        return this;
    }

    /**
     * Sets the charset property
     *
     * @param charset The new value of the charset property
     * @return this class
     */
    public ConnectSettingsManager characterEncoding(StandardCharsets charset) {
        settingValues.put("characterEncoding", charset.toString());
        return this;
    }

    /**
     * Sets the connectTimeout property
     *
     * @param connectTimeout The new value of the connectTimeout property
     * @return this class
     */
    public ConnectSettingsManager connectTimeout(int connectTimeout) {
        settingValues.put("connectTimeout", connectTimeout);
        return this;
    }

    /**
     * Sets the socketTimeout property
     *
     * @param socketTimeout The new value of the socketTimeout property
     * @return this class
     */
    public ConnectSettingsManager socketTimeout(int socketTimeout) {
        settingValues.put("socketTimeout", socketTimeout);
        return this;
    }

    /**
     * Sets the tcpKeepAlive property
     *
     * @param tcpKeepAlive The new value of the tcpKeepAlive property
     * @return this class
     */
    public ConnectSettingsManager tcpKeepAlive(boolean tcpKeepAlive) {
        settingValues.put("tcpKeepAlive", tcpKeepAlive);
        return this;
    }

    /**
     * Sets the tcpNoDelay property
     *
     * @param tcpNoDelay The new value of the tcpNoDelay property
     * @return this class
     */
    public ConnectSettingsManager tcpNoDelay(boolean tcpNoDelay) {
        settingValues.put("tcpNoDelay", tcpNoDelay);
        return this;
    }

    /**
     * Sets the tcpRcvBuf property
     *
     * @param tcpRcvBuf The new value of the tcpRcvBuf property
     * @return this class
     */
    public ConnectSettingsManager tcpRcvBuf(int tcpRcvBuf) {
        settingValues.put("tcpRcvBuf", tcpRcvBuf);
        return this;
    }

    /**
     * Sets the tcpSndBuf property
     *
     * @param tcpSndBuf The new value of the tcpSndBuf property
     * @return this class
     */
    public ConnectSettingsManager tcpSndBuf(int tcpSndBuf) {
        settingValues.put("tcpSndBuf", tcpSndBuf);
        return this;
    }

    /**
     * Sets the maxAllowedPacket property
     *
     * @param maxAllowedPacket The new value of the maxAllowedPacket property
     * @return this class
     */
    public ConnectSettingsManager maxAllowedPacket(int maxAllowedPacket) {
        settingValues.put("maxAllowedPacket", maxAllowedPacket);
        return this;
    }

    /**
     * Sets the tcpTrafficClass property
     *
     * @param tcpTrafficClass The new value of the tcpTrafficClass property
     * @return this class
     */
    public ConnectSettingsManager tcpTrafficClass(int tcpTrafficClass) {
        settingValues.put("tcpTrafficClass", tcpTrafficClass);
        return this;
    }

    /**
     * Sets the useCompression property
     *
     * @param useCompression The new value of the useCompression property
     * @return this class
     */
    public ConnectSettingsManager useCompression(boolean useCompression) {
        settingValues.put("useCompression", useCompression);
        return this;
    }

    /**
     * Sets the useUnbufferedInput property
     *
     * @param useUnbufferedInput The new value of the useUnbufferedInput property
     * @return this class
     */
    public ConnectSettingsManager useUnbufferedInput(boolean useUnbufferedInput) {
        settingValues.put("useUnbufferedInput", useUnbufferedInput);
        return this;
    }

    /**
     * Sets the paranoid property
     *
     * @param paranoid The new value of the paranoid property
     * @return this class
     */
    public ConnectSettingsManager paranoid(boolean paranoid) {
        settingValues.put("paranoid", paranoid);
        return this;
    }

    /**
     * Sets the verifyServerCertificate property
     *
     * @param verifyServerCertificate The new value of the verifyServerCertificate property
     * @return this class
     */
    public ConnectSettingsManager verifyServerCertificate(boolean verifyServerCertificate) {
        settingValues.put("verifyServerCertificate", verifyServerCertificate);
        return this;
    }

    /**
     * Creates the connection string
     *
     * @return the connection string
     */
    public String generateConnectionString() {
        StringBuilder builder = new StringBuilder();

        Object[] keys = settingValues.keySet().toArray();
        Object[] values = settingValues.values().toArray();

        for (int i = 0; i < settingValues.size(); i++) {
            if (i == 0) builder.append("?");
            else builder.append("&");

            builder.append(keys[i]).append("=").append(values[i]);
        }
        return builder.toString();
    }

}
