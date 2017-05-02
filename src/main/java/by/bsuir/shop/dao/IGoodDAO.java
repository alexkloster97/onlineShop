package by.bsuir.shop.dao;

import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.User;

import java.util.List;
/**
 * GoodDAO interface
 */
public interface IGoodDAO {
    /**
     * Create new good in db
     * @param good          good to create
     * @throws DAOException
     */
    void createGood(Good good) throws DAOException;

    /**
     * Read goods from db
     * @return              list of goods
     * @throws DAOException
     */
    List<Good> readGood() throws DAOException;

    /**
     * Read unique good from db
     * @param goodId        id to read
     * @return              good from db
     * @throws DAOException
     */
    Good readGood(Integer goodId) throws DAOException;

    /**
     * Update good in db
     * @param good          good to update
     * @throws DAOException
     */
    void updateGood(Good good) throws DAOException;

    /**
     * Delete good from db
     * @param good          good to delete
     * @throws DAOException
     */
    void deleteGood(Good good) throws DAOException;

    /**
     * Read number of goods in category
     * @param categoryId    category id to read
     * @return              number of goods
     * @throws DAOException
     */
    int readNumGoodsByCategory(Integer categoryId) throws DAOException;

    /**
     * Read goods by category
     * @param categoryId    category id to read
     * @param startPage     start element
     * @return              list of goods
     * @throws DAOException
     */
    List<Good> readGoodByCategory(Integer categoryId, Integer startPage) throws DAOException;

    /**
     * Read goods by name
     * @param value         name to search
     * @return              list of goods
     * @throws DAOException
     */
    List<Good> readGoodByName(String value) throws DAOException;
}
