package by.bsuir.shop.service.admin.good;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Edit good commit command
 */
public class GoodsEditCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(GoodsEditCommand.class);
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
        String page = null;

        try {
            Good good = new Good();
            good.setGoodId(Integer.parseInt(request.getParameter("goodId")));
            good.setName(request.getParameter("name"));
            good.setAbout(request.getParameter("about"));
            good.setCategory(categoryDAO.readCategory(Integer.parseInt(request.getParameter("categoryId"))));
            good.setPrice(Integer.parseInt(request.getParameter("price")));

            goodDAO.updateGood(good);

            page = new GoodsStartCommand().execute(request);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
