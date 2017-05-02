package by.bsuir.shop.service.cart;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.*;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.dao.impl.OrderDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * List goods in cart by user command
 */
public class CartListCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CartListCommand.class);
    private IOrderDAO orderDAO = new OrderDAO();

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
            User user = (User) request.getSession(true).getAttribute("user");
            List<Order> orderList = orderDAO.readOrderByUser(user);
            request.setAttribute("orderList", orderList);

            page = JspConfig.getProperty(JspConfig.CART);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
