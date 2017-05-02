package by.bsuir.shop.service.user;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.util.PasswordEncrypter;
import by.bsuir.shop.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
 * Register new user command
 */
public class RegisterCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(RegisterCommand.class);
    public static final String USER_RIGHTS = "ROLE_USER";

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

        if(Validator.validateRegisterForm(request)) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String phoneNo = request.getParameter("phone-no");

            try {
                User user = new User();
                user.setLogin(login);
                user.setPassword(PasswordEncrypter.encrypt(password));
                user.setPhoneNo(phoneNo);
                user.setRights(USER_RIGHTS);
                user.setState(true);

                userDAO.createUser(user);

                page = JspConfig.getProperty(JspConfig.REGISTER_OK);
            } catch (DAOException | NoSuchAlgorithmException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            request.setAttribute("errorSet", Validator.getErrorSet());
            page = JspConfig.getProperty(JspConfig.REGISTER);
        }

        return page;
    }
}
