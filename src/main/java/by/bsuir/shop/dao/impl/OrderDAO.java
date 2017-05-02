package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.IOrderDAO;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.db.AbstractDAO;
import by.bsuir.shop.db.DBException;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OrderDAO realization
 */
public class OrderDAO extends AbstractDAO implements IOrderDAO {
    public static final Logger LOGGER = Logger.getLogger(OrderDAO.class);

    public static final String SELECT_ORDERS =
            "SELECT * FROM `order` WHERE Submited = 1 AND Delivered = 0 ORDER BY UserID";
    public static final String DELETE_ORDER =
            "DELETE FROM `order` WHERE OrderID = ?";
    public static final String UPDATE_ORDER =
            "UPDATE `order` SET Number = ?, Submited =?, Delivered = ? WHERE OrderID = ?";
    public static final String SELECT_ORDERS_BY_ID =
            "SELECT * FROM `order` WHERE OrderID = ?";
    public static final String SELECT_ORDERS_BETWEEN_DATES =
            "SELECT * FROM `order` WHERE Date BETWEEN ? AND ? AND Submited = 1";
    public static final String SELECT_ORDERS_BY_USER =
            "SELECT * FROM `order` WHERE Submited = 0 AND UserID = ?";
    public static final String INSERT_ORDER =
            "INSERT INTO `order` (Date, UserID, GoodID) VALUES(?, ?, ?)";

    private IGoodDAO goodDAO = new GoodDAO();
    private IUserDAO userDAO = new UserDAO();

    /**
     * Create order in db
     * @param order         order to create
     * @throws DAOException
     */
    @Override
    public void createOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_ORDER);
            preparedStatement.setDate(1, new java.sql.Date(order.getDate().getTime()));
            preparedStatement.setInt(2, order.getUser().getUserId());
            preparedStatement.setInt(3, order.getGood().getGoodId());
            preparedStatement.executeUpdate();
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
    }

    /**
     * Read order from db
     * @return              list of orders
     * @throws DAOException
     */
    @Override
    public List<Order> readOrder() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Order> orderList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Order order = new Order();
                order.setDate(resultSet.getDate("Date"));
                order.setDelivered(resultSet.getBoolean("Delivered"));
                order.setNumber(resultSet.getInt("Number"));
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setSubmited(resultSet.getBoolean("Submited"));

                User user = userDAO.readUser(resultSet.getInt("UserID"));
                order.setUser(user);

                Good good = goodDAO.readGood(resultSet.getInt("GoodID"));
                order.setGood(good);

                orderList.add(order);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return orderList;
    }

    /**
     * Read orders from db between dates
     * @param low           first date
     * @param high          second date
     * @return              list of orders
     * @throws DAOException
     */
    @Override
    public List<Order> readOrder(Date low, Date high) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Order> orderList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDERS_BETWEEN_DATES);
            preparedStatement.setDate(1, new java.sql.Date(low.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(high.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Order order = new Order();
                order.setDate(resultSet.getDate("Date"));
                order.setDelivered(resultSet.getBoolean("Delivered"));
                order.setNumber(resultSet.getInt("Number"));
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setSubmited(resultSet.getBoolean("Submited"));

                User user = userDAO.readUser(resultSet.getInt("UserID"));
                order.setUser(user);

                Good good = goodDAO.readGood(resultSet.getInt("GoodID"));
                order.setGood(good);

                orderList.add(order);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return orderList;
    }

    /**
     * Read unique order from db
     * @param orderId       order id to read
     * @return              order from db
     * @throws DAOException
     */
    @Override
    public Order readOrder(Integer orderId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = new Order();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_ID);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                order.setDate(resultSet.getDate("Date"));
                order.setDelivered(resultSet.getBoolean("Delivered"));
                order.setNumber(resultSet.getInt("Number"));
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setSubmited(resultSet.getBoolean("Submited"));

                User user = userDAO.readUser(resultSet.getInt("UserID"));
                order.setUser(user);

                Good good = goodDAO.readGood(resultSet.getInt("GoodID"));
                order.setGood(good);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return order;
    }

    /**
     * Update order in db
     * @param order         order to update
     * @throws DAOException
     */
    @Override
    public void updateOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            preparedStatement.setInt(1, order.getNumber());
            preparedStatement.setBoolean(2, order.getSubmited());
            preparedStatement.setBoolean(3, order.getDelivered());
            preparedStatement.setInt(4, order.getOrderId());
            preparedStatement.executeUpdate();
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
    }

    /**
     * Delete order from db
     * @param order         order to delete
     * @throws DAOException
     */
    @Override
    public void deleteOrder(Order order) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_ORDER);
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.executeUpdate();
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
    }

    /**
     * Read orders by user
     * @param user          user to read
     * @return              list of orders
     * @throws DAOException
     */
    @Override
    public List<Order> readOrderByUser(User u) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Order> orderList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER);
            preparedStatement.setInt(1, u.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Order order = new Order();
                order.setDate(resultSet.getDate("Date"));
                order.setDelivered(resultSet.getBoolean("Delivered"));
                order.setNumber(resultSet.getInt("Number"));
                order.setOrderId(resultSet.getInt("OrderID"));
                order.setSubmited(resultSet.getBoolean("Submited"));

                User user = userDAO.readUser(resultSet.getInt("UserID"));
                order.setUser(user);

                Good good = goodDAO.readGood(resultSet.getInt("GoodID"));
                order.setGood(good);

                orderList.add(order);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return orderList;
    }
}
