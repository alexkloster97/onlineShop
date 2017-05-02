package by.bsuir.shop.service.cart;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.IOrderDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.dao.impl.OrderDAO;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Add new goods to cart command
 */
public class CartAddCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CartAddCommand.class);
    private IOrderDAO orderDAO = new OrderDAO();
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
            Order order = new Order();
            order.setDate(new Date());

            User user = (User) request.getSession().getAttribute("user");
            order.setUser(user);

            Integer goodId = Integer.parseInt(request.getParameter("goodId"));
            Good good = goodDAO.readGood(goodId);
            order.setGood(good);

            orderDAO.createOrder(order);

            page = new CartListCommand().execute(request);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
