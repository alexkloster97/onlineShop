package by.bsuir.shop.db;

import by.bsuir.shop.config.DBConfig;
import com.mysql.jdbc.Driver;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection Pool (CP)
 */
public class ConnectionPool {
    public static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    /**
     * Part of singletone
     */
    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();

    private BlockingQueue<Connection> connections;
    private boolean flag;

    /**
     * Part of singletone
     */
    private ConnectionPool() {
        String url = DBConfig.getProperty(DBConfig.URL);
        String user = DBConfig.getProperty(DBConfig.USER);
        String password = DBConfig.getProperty(DBConfig.PASSWORD);
        Integer poolSize = Integer.parseInt(DBConfig.getProperty(DBConfig.POOL_SIZE));

        try {
            DriverManager.registerDriver(new Driver());
            connections = new ArrayBlockingQueue<>(poolSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for(int i=0; i<poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, user, password);

                if(!connections.offer(connection)) {
                    throw new RuntimeException("Can't initialize pool properly");
                }
            } catch (SQLException e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            }
        }

        flag = true;
    }

    /**
     * Part of singletone
     * @return          CP instance
     */
    public static ConnectionPool getInstance() {
        if(instance==null) {
            try {
                lock.lock();

                instance = new ConnectionPool();
            } finally {
                lock.unlock();
            }
        }

        return instance;
    }

    /**
     * Get connection from pool
     * @return              sql connection
     * @throws DBException
     */
    public Connection getConnection() throws DBException {
        if(flag) {
            try {
                Connection connection = connections.take();

                return connection;
            } catch (InterruptedException e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.error("Pool is not working");
            throw new DBException("Pool is not working");
        }
    }

    /**
     * Return connection to pool
     * @param connection    sql connection
     */
    public void releaseConnection(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                if(!connections.offer(connection)) {
                    LOGGER.error("Error returning connection to pool");
                    throw new RuntimeException("Error returning connection to pool");
                }
            } else {
                LOGGER.error("Error returning connection to pool");
                throw new RuntimeException("Error returning connection to pool");
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Releases pool
     */
    public void releasePool() {
        Integer poolSize = Integer.parseInt(DBConfig.getProperty(DBConfig.POOL_SIZE));

        while(!connections.isEmpty()) {
            try {
                Connection connection = connections.take();

                connection.close();
            } catch (InterruptedException | SQLException e) {
                LOGGER.error(e);
            }
        }
    }
}
