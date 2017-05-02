package by.bsuir.shop.controller;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.db.ConnectionPool;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.service.admin.category.CategoryStartCommand;
import by.bsuir.shop.service.admin.good.GoodsAddCommand;
import by.bsuir.shop.util.CommandMultipartUtil;
import by.bsuir.shop.util.CommandUtil;
import by.bsuir.shop.util.ServletUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Servlet class
 */
@WebServlet(name="ShopServlet", urlPatterns = "/controller")
public class ShopServlet extends HttpServlet {
    public static Logger LOGGER = Logger.getLogger(ShopServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process requests from users
     *
     * @param request           request to process
     * @param response          response
     * @throws ServletException in case of exception
     * @throws IOException      in case of exception
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;

        page = JspConfig.getProperty(JspConfig.INDEX);

        try {
            String commandName = request.getParameter("command");
            ICommand command = null;

            if(!ServletFileUpload.isMultipartContent(request)) {
                String name = request.getParameter("command");
                command = CommandUtil.getCommand(name);
            } else {
                command = CommandMultipartUtil.getCommand(request);
            }

            page = command.execute(request);
            ServletUtil.setCategory(request);
        } catch (ServiceException e) {
            e.printStackTrace();
            page = JspConfig.getProperty(JspConfig.ERROR);
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    public void init(ServletConfig sce) throws ServletException {
        super.init();
        Properties logProperties = new Properties();
        try {
            System.out.println(sce.getServletContext().getRealPath("log4j.properties"));
            logProperties.load(new FileInputStream(sce.getServletContext().getRealPath("log4j.properties")));
            PropertyConfigurator.configure(logProperties);
            LOGGER.info("Logging initialized");
        } catch (IOException e) {
            LOGGER.error(e);
        }

        ConnectionPool.getInstance();
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().releasePool();
    }
}
