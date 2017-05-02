package by.bsuir.shop.dao.impl;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.db.AbstractDAO;
import by.bsuir.shop.db.DBException;
import by.bsuir.shop.domain.Category;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CategoryDAO realization
 */
public class CategoryDAO extends AbstractDAO implements ICategoryDAO {
    public static final Logger LOGGER = Logger.getLogger(CategoryDAO.class);

    public static final String SELECT_CATEGORY =
            "SELECT * FROM `category`";
    public static final String INSERT_CATEGORY =
            "INSERT INTO `category` (Name, Path) VALUES (?, ?)";
    public static final String UPDATE_CATERORY =
            "UPDATE `category` SET Name = ?, Path = ? WHERE CategoryID = ?";
    public static final String DELETE_CATEGORY =
            "DELETE FROM `category` WHERE CategoryID = ?";
    public static final String SELECT_CATEGORY_BY_ID =
            "SELECT * FROM `category` WHERE CategoryID = ?";

    /**
     * Create new category entry in db
     *
     * @param category      new category
     * @throws DAOException
     */
    @Override
    public void createCategory(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_CATEGORY);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getPath());
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
     * Reads categories from db
     *
     * @return              list of categories
     * @throws DAOException
     */
    @Override
    public List<Category> readCategory() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Category> categoryList = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CATEGORY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Category category = new Category();
                category.setName(resultSet.getString("Name"));
                category.setCategoryId(resultSet.getInt("CategoryID"));
                category.setPath(resultSet.getString("Path"));

                categoryList.add(category);
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
        return categoryList;
    }

    /**
     * Read unique category from db
     *
     * @param categoryId    id to read
     * @return              category
     * @throws DAOException
     */
    @Override
    public Category readCategory(Integer categoryId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Category category = new Category();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                category.setName(resultSet.getString("Name"));
                category.setCategoryId(resultSet.getInt("CategoryID"));
                category.setPath(resultSet.getString("Path"));
            }
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }

        return category;
    }

    /**
     * Updates category in db
     *
     * @param category      category to update
     * @throws DAOException
     */
    @Override
    public void updateCategory(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CATERORY);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getPath());
            preparedStatement.setInt(3, category.getCategoryId());
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
     * Delete category from db
     * @param category      category to delete
     * @throws DAOException
     */
    @Override
    public void deleteCategory(Category category) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(DELETE_CATEGORY);
            preparedStatement.setInt(1, category.getCategoryId());
            preparedStatement.executeUpdate();
        } catch (DBException | SQLException e) {
            LOGGER.error(e);
            throw new DAOException(e);
        } finally {
            closePreparedStatement(preparedStatement);
            releaseConnection(connection);
        }
    }
}
