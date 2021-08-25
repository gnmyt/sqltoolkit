import de.gnmyt.sqltoolkit.drivers.MySQLConnection;
import de.gnmyt.sqltoolkit.storage.SQLStorageMedium;

public class StorageMediumExample {

    public static void main(String[] args) {

        // First we need to connect to the mysql database
        MySQLConnection connection = new MySQLConnection("localhost", "root", "password", "database").connect();

        // Then we can register our user table
        connection.getTableFactory().register(new StorageMediumExample.ConfigurationStorage(connection));

        // Now we can get the storage from our table factory
        ConfigurationStorage storage = (ConfigurationStorage) connection.getTableFactory().getStorage(ConfigurationStorage.class);

        // Here we can use the methods that we created in the storage
        storage.updateLanguage("en");
        storage.updateVersion("1.0");

        // We can also get a value by using the storage medium
        String version = storage.getVersion();

        // If you want to you can list all entries from the current storage
        storage.getEntries().forEach(entry ->
                System.out.println( entry.get("keyName") + " -> " + entry.get("value") ));
    }


    public static class ConfigurationStorage extends SQLStorageMedium {

        public ConfigurationStorage(MySQLConnection connection) {
            super(connection);
        }

        @Override
        protected String tableName() {
            return "config_storage";
        }

        // Here we can add some example methods to test the storage medium

        /**
         * Updates the language of the configuration storage
         * @param newLanguage The new language
         */
        public void updateLanguage(String newLanguage) {
            insertOrUpdate("language", newLanguage);
        }

        /**
         * Gets the current language of the configuration storage
         * @return The current language of the configuration storage
         */
        public String getLanguage() {
            return get("language");
        }

        /**
         * Updates the version of the configuration storage
         * @param newVersion The new version
         */
        public void updateVersion(String newVersion) {
            insertOrUpdate("version", newVersion);
        }

        /**
         * Gets the current version of the configuration storage
         * @return The current version of the configuration storage
         */
        public String getVersion() {
            return get("version");
        }
    }

}
