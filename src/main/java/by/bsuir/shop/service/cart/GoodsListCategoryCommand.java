package by.bsuir.shop.service.cart;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import java.util.List;

/**
 * List goods by category command
 */
public class GoodsListCategoryCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(GoodsListCategoryCommand.class);
    private IGoodDAO goodDAO = new GoodDAO();
    private ICategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        Integer categoryId = Integer.parseInt(request.getParameter("categoryId"));
        Integer startPage = 0;


        if(request.getParameter("startPage")!=null) {

            startPage = Integer.parseInt(request.getParameter("startPage"));
        }

        try {
            int num = goodDAO.readNumGoodsByCategory(categoryId);
            request.setAttribute("numGoods", num);
            List<Good> goodList = goodDAO.readGoodByCategory(categoryId, startPage);
            request.setAttribute("goodList", goodList);
            Category category = categoryDAO.readCategory(categoryId);
            request.setAttribute("category", category);
            request.setAttribute("startPage", startPage);
            request.setAttribute("pageNo", request.getParameter("pageNo")!=null?request.getParameter("pageNo"):"1");
            request.setAttribute("lastPage", num/8);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return JspConfig.getProperty(JspConfig.GOODS_BY_CATEGORY);
    }
}
