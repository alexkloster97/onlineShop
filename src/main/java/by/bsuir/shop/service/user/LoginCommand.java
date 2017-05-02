package by.bsuir.shop.service.user;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Process login command
 */
public class LoginCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

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

        if(Validator.validateLoginForm(request)) {
            try {
                User user = userDAO.readUser(request.getParameter("login"));
                request.getSession(true).setAttribute("user", user);
                page = JspConfig.getProperty(JspConfig.INDEX);
            } catch (DAOException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            page = JspConfig.getProperty(JspConfig.LOGIN);
        }

        return page;
    }
}
