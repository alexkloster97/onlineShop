package by.bsuir.shop.service.admin.category;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * View category list page command
 */
public class CategoryStartCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CategoryStartCommand.class);
    private ICategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page = null;

        try {
            List<Category> categoryList = categoryDAO.readCategory();
            request.setAttribute("categoryList", categoryList);

            page = JspConfig.getProperty(JspConfig.ADMIN_CATEGORY);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
