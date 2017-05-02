package by.bsuir.shop.service.admin.category;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Delete category command
 */
public class CategoryDeleteCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CategoryDeleteCommand.class);
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
            Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = new Category();
            category.setCategoryId(categoryId);
            categoryDAO.deleteCategory(category);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        page = new CategoryStartCommand().execute(request);

        return page;
    }
}
