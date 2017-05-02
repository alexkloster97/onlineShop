package by.bsuir.shop.service.admin.category;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Edit category command
 */
public class CategoryEditCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CategoryEditCommand.class);
    private ICategoryDAO categoryDAO = new CategoryDAO();

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page = null;

        if(Validator.validateCategoryForm(request)) {
            Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
            Category category = new Category();
            category.setName(request.getParameter("name"));
            category.setPath(request.getParameter("path"));
            category.setCategoryId(categoryId);

            try {
                categoryDAO.updateCategory(category);
            } catch (DAOException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            request.setAttribute("errorSetEdit", Validator.getErrorSet());
        }

        page = new CategoryStartCommand().execute(request);

        return page;
    }
}
