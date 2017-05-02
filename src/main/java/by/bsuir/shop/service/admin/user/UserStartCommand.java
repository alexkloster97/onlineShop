package by.bsuir.shop.service.admin.user;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Open user list view command
 */
public class UserStartCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(UserStartCommand.class);
    private IUserDAO userDAO = new UserDAO();

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
            List<User> userList = userDAO.readUser();
            request.setAttribute("userList", userList);

            page = JspConfig.getProperty(JspConfig.ADMIN_USER);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
