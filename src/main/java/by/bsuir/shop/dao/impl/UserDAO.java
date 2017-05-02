package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.db.AbstractDAO;
import by.bsuir.shop.db.DBException;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

/**
 * UserDAO realization
 */
public class UserDAO extends AbstractDAO implements IUserDAO {
    public static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    public static final String INSERT_USER =
            "INSERT INTO `user` (Login, Password, Rights, State, PhoneNo) VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_USER_BY_LOGIN =
            "SELECT * FROM `user` WHERE Login = ?";
    public static final String SELECT_USER_BY_LOGIN_AND_PASSWORD =
            "SELECT * FROM `user` WHERE Login = ? AND Password = ? AND State = 1";
    public static final String SELECT_USER =
            "SELECT * FROM `user`";
    public static final String SELECT_USER_BY_ID =
            "SELECT * FROM `user` WHERE UserID = ?";
    public static final String UPDATE_USER =
            "UPDATE `user` SET State=?, Rights = ? WHERE UserID = ?";
    public static final String DELETE_USER =
            "DELETE FROM `user` WHERE UserID = ?";

    /**
     * Create user in db
     * @param user          user to create
     * @throws DAOException
     */
    @Override
    public void createUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRights());
            preparedStatement.setBoolean(4, user.getState());
            preparedStatement.setString(5, user.getPhoneNo());
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
     * Read users from db
     * @return              list of users
     * @throws DAOException
     */
    @Override
    public List<User> readUser() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<User> userList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                User user = new User();
                user.setPassword(resultSet.getString("Password"));
                user.setLogin(resultSet.getString("Login"));
                user.setState(resultSet.getBoolean("State"));
                user.setRights(resultSet.getString("Rights"));
                user.setPhoneNo(resultSet.getString("PhoneNo"));
                user.setUserId(resultSet.getInt("UserID"));

                userList.add(user);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return userList;
    }

    /**
     * Read unique user
     * @param userId        user id to read
     * @return              user from db
     * @throws DAOException
     */
    @Override
    public User readUser(Integer userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = new User();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                user.setPassword(resultSet.getString("Password"));
                user.setLogin(resultSet.getString("Login"));
                user.setState(resultSet.getBoolean("State"));
                user.setRights(resultSet.getString("Rights"));
                user.setPhoneNo(resultSet.getString("PhoneNo"));
                user.setUserId(resultSet.getInt("UserID"));
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return user;
    }

    /**
     * Update user in db
     * @param user          user to update
     * @throws DAOException
     */
    @Override
    public void updateUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setBoolean(1, user.getState());
            preparedStatement.setString(2, user.getRights());
            preparedStatement.setInt(3, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (DBException |SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
    }

    /**
     * Delete user from db
     * @param user          user to delete
     * @throws DAOException
     */
    @Override
    public void deleteUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER);
            preparedStatement.setInt(1, user.getUserId());
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
     * Check if login is unique
     * @param login         login to check
     * @return              true or false
     * @throws DAOException
     */
    @Override
    public boolean hasSameLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return true;
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return false;
    }

    /**
     * Check if user exists
     * @param user          user to check
     * @return              true or false
     * @throws DAOException
     */
    @Override
    public boolean authorization(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return true;
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return false;
    }

    /**
     * Read unique user by login
     * @param login         login to read
     * @return              user from db
     * @throws DAOException
     */
    @Override
    public User readUser(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = new User();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                user.setPassword(resultSet.getString("Password"));
                user.setLogin(resultSet.getString("Login"));
                user.setState(resultSet.getBoolean("State"));
                user.setRights(resultSet.getString("Rights"));
                user.setPhoneNo(resultSet.getString("PhoneNo"));
                user.setUserId(resultSet.getInt("UserID"));
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return user;
    }
}
