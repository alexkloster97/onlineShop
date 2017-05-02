package by.bsuir.shop.dao;

import by.bsuir.shop.domain.User;

import java.util.List;

/**
 * UserDAO interface
 */
public interface IUserDAO {
    /**
     * Create user in db
     * @param user          user to create
     * @throws DAOException
     */
    void createUser(User user) throws DAOException;

    /**
     * Read users from db
     * @return              list of users
     * @throws DAOException
     */
    List<User> readUser() throws DAOException;

    /**
     * Read unique user
     * @param userId        user id to read
     * @return              user from db
     * @throws DAOException
     */
    User readUser(Integer userId) throws DAOException;

    /**
     * Update user in db
     * @param user          user to update
     * @throws DAOException
     */
    void updateUser(User user) throws DAOException;

    /**
     * Delete user from db
     * @param user          user to delete
     * @throws DAOException
     */
    void deleteUser(User user) throws DAOException;

    /**
     * Check if login is unique
     * @param login         login to check
     * @return              true or false
     * @throws DAOException
     */
    boolean hasSameLogin(String login) throws DAOException;

    /**
     * Check if user exists
     * @param user          user to check
     * @return              true or false
     * @throws DAOException
     */
    boolean authorization(User user) throws DAOException;

    /**
     * Read unique user by login
     * @param login         login to read
     * @return              user from db
     * @throws DAOException
     */
    User readUser(String login) throws DAOException;
}
