package by.bsuir.shop.service.cart;

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
import java.util.List;

/**
 * Find goods by name command
 */
public class CartFindCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CartFindCommand.class);
    private IGoodDAO goodDAO = new GoodDAO();

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
            String value = request.getParameter("value");
            List<Good> goodList = goodDAO.readGoodByName(value);
            request.setAttribute("goodList", goodList);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return JspConfig.getProperty(JspConfig.GOODS_BY_CATEGORY);
    }
}
