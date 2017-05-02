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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Find orders between dates command
 */
public class OrderFilterCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(OrderFilterCommand.class);
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date low = dateFormat.parse(request.getParameter("low"));
            Date high = dateFormat.parse(request.getParameter("high"));

            List<Order> orderList = orderDAO.readOrder(low, high);
            request.setAttribute("orderList", orderList);

            page = JspConfig.getProperty(JspConfig.ADMIN_ORDERS);
        } catch (DAOException | ParseException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
