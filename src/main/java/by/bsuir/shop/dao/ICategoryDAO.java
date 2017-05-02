package by.bsuir.shop.dao;

import by.bsuir.shop.domain.Category;

import java.util.List;

/**
 * CategoryDAO interface
 */
public interface ICategoryDAO {
    /**
     * Create new category entry in db
     *
     * @param category      new category
     * @throws DAOException
     */
    void createCategory(Category category) throws DAOException;

    /**
     * Reads categories from db
     *
     * @return              list of categories
     * @throws DAOException
     */
    List<Category> readCategory() throws DAOException;

    /**
     * Read unique category from db
     *
     * @param categoryId    id to read
     * @return              category
     * @throws DAOException
     */
    Category readCategory(Integer categoryId) throws DAOException;

    /**
     * Updates category in db
     *
     * @param category      category to update
     * @throws DAOException
     */
    void updateCategory(Category category) throws DAOException;

    /**
     * Delete category from db
     * @param category      category to delete
     * @throws DAOException
     */
    void deleteCategory(Category category) throws DAOException;
}
