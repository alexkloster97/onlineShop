package by.bsuir.shop.util;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Util class
 */
public class ServletUtil {
    public static final Logger LOGGER = Logger.getLogger(ServletUtil.class);

    private static ICategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Put list of category to request
     * @param request           request
     * @throws ServiceException
     */
    public static void setCategory(HttpServletRequest request) throws ServiceException {
        try {
            request.setAttribute("categoryList", categoryDAO.readCategory());
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

    }
}

