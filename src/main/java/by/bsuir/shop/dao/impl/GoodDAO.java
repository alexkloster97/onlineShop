package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.db.AbstractDAO;
import by.bsuir.shop.db.DBException;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GoodDAO realization
 */
public class GoodDAO extends AbstractDAO implements IGoodDAO {
    public static final Logger LOGGER = Logger.getLogger(GoodDAO.class);

    public static final String SELECT_GOODS =
            "SELECT * FROM `good`";
    public static final String INSERT_GOODS =
            "INSERT INTO `good` (Price, Name, About, Path, CategoryID) VALUES(?, ?, ?, ?, ?)";
    public static final String DELETE_GOODS =
            "DELETE FROM `good` WHERE GoodID = ?";
    public static final String SELECT_GOODS_BY_ID =
            "SELECT * FROM `good` WHERE GoodID = ?";
    public static final String UPDATE_GOODS =
            "UPDATE `good` SET Name = ?, Price = ?, About = ?, CategoryID = ? WHERE GoodID = ?";
    public static final String SELECT_NUM_GOODS_BY_CATEGORY =
            "SELECT COUNT(*) FROM `good` WHERE CategoryID = ?";
    public static final String SELECT_GOODS_BY_CATEGORY =
            "SELECT * FROM `good` WHERE CategoryId = ? LIMIT ?, 8";
    public static final String SELECT_GOODS_BY_NAME =
            "SELECT * FROM `good` WHERE Name LIKE ?";

    private ICategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Create new good in db
     * @param good          good to create
     * @throws DAOException
     */
    @Override
    public void createGood(Good good) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_GOODS);
            preparedStatement.setInt(1, good.getPrice());
            preparedStatement.setString(2, good.getName());
            preparedStatement.setString(3, good.getAbout());
            preparedStatement.setString(4, good.getPath());
            preparedStatement.setInt(5, good.getCategory().getCategoryId());
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
     * Read goods from db
     * @return              list of goods
     * @throws DAOException
     */
    @Override
    public List<Good> readGood() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Good> goodList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_GOODS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Good good = new Good();
                good.setName(resultSet.getString("Name"));
                good.setAbout(resultSet.getString("About"));
                good.setGoodId(resultSet.getInt("GoodId"));
                good.setPath(resultSet.getString("Path"));
                good.setPrice(resultSet.getInt("Price"));

                Category category = categoryDAO.readCategory(resultSet.getInt("CategoryID"));
                good.setCategory(category);

                goodList.add(good);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return goodList;
    }

    /**
     * Read unique good from db
     * @param goodId        id to read
     * @return              good from db
     * @throws DAOException
     */
    @Override
    public Good readGood(Integer goodId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Good good = new Good();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_GOODS_BY_ID);
            preparedStatement.setInt(1, goodId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                good.setName(resultSet.getString("Name"));
                good.setAbout(resultSet.getString("About"));
                good.setGoodId(resultSet.getInt("GoodId"));
                good.setPath(resultSet.getString("Path"));
                good.setPrice(resultSet.getInt("Price"));

                Category category = categoryDAO.readCategory(resultSet.getInt("CategoryID"));
                good.setCategory(category);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return good;
    }

    /**
     * Update good in db
     * @param good          good to update
     * @throws DAOException
     */
    @Override
    public void updateGood(Good good) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_GOODS);
            preparedStatement.setString(1, good.getName());
            preparedStatement.setInt(2, good.getPrice());
            preparedStatement.setString(3, good.getAbout());
            preparedStatement.setInt(4, good.getCategory().getCategoryId());
            preparedStatement.setInt(5, good.getGoodId());
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
     * Delete good from db
     * @param good          good to delete
     * @throws DAOException
     */
    @Override
    public void deleteGood(Good good) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_GOODS);
            preparedStatement.setInt(1, good.getGoodId());
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
     * Read number of goods in category
     * @param categoryId    category id to read
     * @return              number of goods
     * @throws DAOException
     */
    @Override
    public int readNumGoodsByCategory(Integer categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int number = 0;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_NUM_GOODS_BY_CATEGORY);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                number = resultSet.getInt(1);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return number;
    }

    /**
     * Read goods by category
     * @param categoryId    category id to read
     * @param startPage     start element
     * @return              list of goods
     * @throws DAOException
     */
    @Override
    public List<Good> readGoodByCategory(Integer categoryId, Integer startPage) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Good> goodList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_GOODS_BY_CATEGORY);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, startPage*8);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Good good = new Good();
                good.setName(resultSet.getString("Name"));
                good.setAbout(resultSet.getString("About"));
                good.setGoodId(resultSet.getInt("GoodId"));
                good.setPath(resultSet.getString("Path"));
                good.setPrice(resultSet.getInt("Price"));

                Category category = categoryDAO.readCategory(resultSet.getInt("CategoryID"));
                good.setCategory(category);

                goodList.add(good);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return goodList;
    }

    /**
     * Read goods by name
     * @param value         name to search
     * @return              list of goods
     * @throws DAOException
     */
    @Override
    public List<Good> readGoodByName(String value) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Good> goodList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_GOODS_BY_NAME);
            preparedStatement.setString(1, '%'+value+'%');
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Good good = new Good();
                good.setName(resultSet.getString("Name"));
                good.setAbout(resultSet.getString("About"));
                good.setGoodId(resultSet.getInt("GoodId"));
                good.setPath(resultSet.getString("Path"));
                good.setPrice(resultSet.getInt("Price"));

                Category category = categoryDAO.readCategory(resultSet.getInt("CategoryID"));
                good.setCategory(category);

                goodList.add(good);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return goodList;
    }
}
