package de.gnmyt.SQLToolkit.types;

public enum LoginParam {

    DEFAULT("useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&useTimezone=true&serverTimezone=UTC"),
    NO_SSL("useSSL=false"),
    USE_SSL("useSSL=true"),
    AUTO_RECONNECT("autoReconnect=true"),
    UTF8_ENCODING("characterEncoding=UTF-8"),
    USE_UNICODE("useUnicode=yes"),
    USE_TIMEZONE("useTimezone=true"),
    TIMEZONE_UTC("serverTimezone=UTC");

    private String value;

    /**
     * Basic constructor for the LoginParam enum
     * @param value JDBC parameter
     */
    LoginParam(String value) {
        this.value = value;
    }

    /**
     * Get the JDBC value
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

}
