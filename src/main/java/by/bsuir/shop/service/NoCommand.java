package by.bsuir.shop.service;

import by.bsuir.shop.config.JspConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * No Command class
 */
public class NoCommand implements ICommand {
    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        return JspConfig.getProperty(JspConfig.INDEX);
    }
}
