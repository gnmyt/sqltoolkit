import de.gnmyt.sqltoolkit.drivers.MySQLConnection;

public class LoginExample {

    public static void main(String[] args) {

        // First we can define the mysql server data into some variables
        String hostname = "localhost";
        String username = "root";
        String password = "password";
        String database = "database";

        // Then we need to create a connection
        MySQLConnection connection = new MySQLConnection(hostname, username, password, database); // Here you need to provide your mysql server data

        // You can now set the settings of the connection (before connecting)
        connection.updateSettings()
                .useSSL(true) // You can set for example the ssl property
                .customProperty("example", ""); // You can also set a custom property


        // Now you can connect to the database
        connection.connect();
    }

}
