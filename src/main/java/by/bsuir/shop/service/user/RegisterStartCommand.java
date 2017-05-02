package by.bsuir.shop.service.user;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Open register page command
 */
public class RegisterStartCommand implements ICommand {
    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return JspConfig.getProperty(JspConfig.REGISTER);
    }
}
