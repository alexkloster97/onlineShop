package by.bsuir.shop.service.admin.good;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Delete good command
 */
public class GoodsDeleteCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(GoodsDeleteCommand.class);
    IGoodDAO goodDAO = new GoodDAO();

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
            Integer goodsId = Integer.parseInt(request.getParameter("goodId"));
            Good good = new Good();
            good.setGoodId(goodsId);

            goodDAO.deleteGood(good);

            page = new GoodsStartCommand().execute(request);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return page;
    }
}
