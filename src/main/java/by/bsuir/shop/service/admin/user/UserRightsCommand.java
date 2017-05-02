package by.bsuir.shop.service.admin.user;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Change user access command
 */
public class UserRightsCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(UserRightsCommand.class);
    public static final String USER_RIGHTS = "ROLE_USER";
    public static final String ADMIN_RIGHTS = "ROLE_ADMIN";

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
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            User user = userDAO.readUser(userId);

            if(USER_RIGHTS.equals(user.getRights())) {
                user.setRights(ADMIN_RIGHTS);
            } else {
                user.setRights(USER_RIGHTS);
            }

            userDAO.updateUser(user);

            page = new UserStartCommand().execute(request);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
