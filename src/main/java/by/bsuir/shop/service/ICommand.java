package by.bsuir.shop.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface
 */
public interface ICommand {
    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    String execute(HttpServletRequest request) throws ServiceException;
}
