package by.bsuir.shop.service;

import by.bsuir.shop.config.JspConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Change Language command
 */
public class LanguageCommand implements ICommand {
    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        request.getSession(true).setAttribute("lang", request.getParameter("loc"));

        return JspConfig.getProperty(JspConfig.INDEX);
    }
}
