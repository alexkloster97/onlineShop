package by.bsuir.shop.dao;

import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.domain.User;

import java.util.Date;
import java.util.List;
/**
 * OrderDAO interface
 */
public interface IOrderDAO {
    /**
     * Create order in db
     * @param order         order to create
     * @throws DAOException
     */
    void createOrder(Order order) throws DAOException;

    /**
     * Read order from db
     * @return              list of orders
     * @throws DAOException
     */
    List<Order> readOrder() throws DAOException;

    /**
     * Read orders from db between dates
     * @param low           first date
     * @param high          second date
     * @return              list of orders
     * @throws DAOException
     */
    List<Order> readOrder(Date low, Date high) throws DAOException;

    /**
     * Read unique order from db
     * @param orderId       order id to read
     * @return              order from db
     * @throws DAOException
     */
    Order readOrder(Integer orderId) throws DAOException;

    /**
     * Update order in db
     * @param order         order to update
     * @throws DAOException
     */
    void updateOrder(Order order) throws DAOException;

    /**
     * Delete order from db
     * @param order         order to delete
     * @throws DAOException
     */
    void deleteOrder(Order order) throws DAOException;

    /**
     * Read orders by user
     * @param user          user to read
     * @return              list of orders
     * @throws DAOException
     */
    List<Order> readOrderByUser(User user) throws DAOException;
}
