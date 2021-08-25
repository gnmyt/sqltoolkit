import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.storage.SQLTable;
import de.gnmyt.sqltoolkit.types.SQLType;
import de.gnmyt.sqltoolkit.types.TableField;

public class TableExample {

    private static MySQLConnection connection;

    public static void main(String[] args) {

        // First we need to connect to the mysql database
        connection = new MySQLConnection("localhost", "root", "password", "database").connect();

        // Then we can register our user table
        connection.getTableFactory().register(new UserTable(connection));

        // Now let's create some users
        createUser("GNM", "germannewsmaker@gmail.com", "Mathias");

        createUser("delete_me", "deleteme@example.com", "DeleteMe");

        // Here we can get the email of our user before we delete him
        String email = getUserMail("delete_me");

        // Now we can delete this user
        deleteUser("delete_me");
    }

    /**
     * Creates a user in the user table
     *
     * @param username   The name of the user (nickname)
     * @param email      The email of the user
     * @param first_name The first name of the user
     */
    public static void createUser(String username, String email, String first_name) {
        SQLTable userTable = connection.getTableFactory().getTable(UserTable.class);

        userTable.insert()
                .value("username", username)
                .value("email", email)
                .value("first_name", first_name)
                .execute();
    }

    /**
     * Deletes a user from the user table
     *
     * @param username The name of the user
     */
    public static void deleteUser(String username) {
        SQLTable userTable = connection.getTableFactory().getTable(UserTable.class);

        userTable.delete()
                .where("username", username)
                .execute();
    }

    /**
     * Gets the email of the user
     *
     * @param username The name of the user
     * @return the email of the user
     */
    public static String getUserMail(String username) {
        SQLTable userTable = connection.getTableFactory().getTable(UserTable.class);

        return userTable.select()
                .where("username", username)
                .getResult()
                .getString("email");
    }

    public static class UserTable extends SQLTable {

        public UserTable(MySQLConnection connection) {
            super(connection);
        }

        @Override
        protected String tableName() {
            return "users";
        }

        @Override
        protected void tableFields() {
            string("username", 255, "");
            custom(new TableField().setType(SQLType.STRING).setName("email").setLength(25).setDefaultValue("test@example.com"));
            custom("password").type(SQLType.STRING).allowNull(true).length(80).add();
            custom().name("first_name").add();
        }

    }

}
