package by.bsuir.shop.service.admin.order;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IOrderDAO;
import by.bsuir.shop.dao.impl.OrderDAO;
import by.bsuir.shop.domain.Order;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * View order list page command
 */
public class OrderStartCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(OrderStartCommand.class);
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
            List<Order> orderlist = orderDAO.readOrder();
            request.setAttribute("orderList", orderlist);

            page = JspConfig.getProperty(JspConfig.ADMIN_ORDERS);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
