package by.bsuir.shop.config;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * DBConfig class
 */
public class DBConfig {
    public static final Logger LOGGER = Logger.getLogger(DBConfig.class);

    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String POOL_SIZE = "pool.size";

    private static final String DB_BOUNDLE_NAME = "jdbc";
    private static ResourceBundle bundle = ResourceBundle.getBundle(DB_BOUNDLE_NAME);

    /**
     * Reads property from jdbc.properties
     *
     * @param key   property to read
     * @return      value of property
     */
    public static String getProperty(String key) {
        if(bundle == null) {
            LOGGER.error("Runtime exception because of CP");
            throw new RuntimeException("Error configuring connection pool");
        }

        return bundle.getString(key);
    }
}
