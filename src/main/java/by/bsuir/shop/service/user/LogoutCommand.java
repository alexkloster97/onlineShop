package by.bsuir.shop.service.user;

import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.NoCommand;
import by.bsuir.shop.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Logout command
 */
public class LogoutCommand implements ICommand  {
    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        request.getSession().setAttribute("user", null);

        return new NoCommand().execute(request);
    }
}
