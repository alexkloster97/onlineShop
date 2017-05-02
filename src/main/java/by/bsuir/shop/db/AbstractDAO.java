package by.bsuir.shop.db;

import by.bsuir.shop.db.ConnectionPool;
import by.bsuir.shop.db.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * AbstractDAO
 */
public class AbstractDAO {
    public static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Gets connection from CP
     * @return              sql conenction
     * @throws DBException
     */
    protected Connection getConnection() throws DBException {
        return connectionPool.getConnection();
    }

    /**
     * Return connection to CP
     * @param connection    sql connection
     */
    protected void releaseConnection(Connection connection) {
        connectionPool.releaseConnection(connection);
    }

    /**
     * Close prepared statement
     * @param preparedStatement prepared statement
     */
    protected void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
