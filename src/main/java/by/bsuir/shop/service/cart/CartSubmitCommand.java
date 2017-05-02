package by.bsuir.shop.service.cart;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IOrderDAO;
import by.bsuir.shop.dao.impl.OrderDAO;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Submit cart command
 */
public class CartSubmitCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CartSubmitCommand.class);
    IOrderDAO orderDAO = new OrderDAO();

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
            Integer orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = orderDAO.readOrder(orderId);
            order.setSubmited(true);
            orderDAO.updateOrder(order);

            page = new CartListCommand().execute(request);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
