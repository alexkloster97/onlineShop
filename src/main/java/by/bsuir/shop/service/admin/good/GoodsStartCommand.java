package by.bsuir.shop.service.admin.good;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * View good list page command
 */
public class GoodsStartCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(GoodsStartCommand.class);
    private IGoodDAO goodDAO = new GoodDAO();
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
            List<Good> goodList = goodDAO.readGood();
            request.setAttribute("goodList", goodList);

            List<Category> categoryList = categoryDAO.readCategory();
            request.setAttribute("categoryList", categoryList);

            page = JspConfig.getProperty(JspConfig.ADMIN_GOODS);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
