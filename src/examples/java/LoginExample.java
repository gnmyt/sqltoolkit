import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.types.LoginParam;

import java.util.Arrays;

public class LoginExample {

    public static void main(String[] args) {

        // First we can define the mysql server data into some variables
        String hostname = "localhost";
        String username = "root";
        String password = "password";
        String database = "database";

        // Then we need to create a connection
        MySQLConnection connection = new MySQLConnection(hostname, username, password, database, // Here you need to provide your mysql server data
                LoginParam.AUTO_RECONNECT); // You can set login parameters in the constructor

        // You can also set the connection string manually (before connecting)
        connection.updateConnectionString(LoginParam.AUTO_RECONNECT,
                LoginParam.USE_SSL,
                LoginParam.UTF8_ENCODING);

        // Now you can connect to the database
        connection.connect();

        // If you want to you can list all login parameters
        Arrays.stream(LoginParam.values()).forEach(System.out::println);
    }

}
