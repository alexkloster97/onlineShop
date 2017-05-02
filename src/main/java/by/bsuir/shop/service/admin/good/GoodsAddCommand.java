package by.bsuir.shop.service.admin.good;

import by.bsuir.shop.config.JspConfig;
import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.IGoodDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.dao.impl.GoodDAO;
import by.bsuir.shop.db.ConnectionPool;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.service.admin.AbstractFileUploadCommand;
import by.bsuir.shop.util.CommandUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Set;

/**
 * Add new good command
 */
public class GoodsAddCommand  extends AbstractFileUploadCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(GoodsAddCommand.class);
    private ICategoryDAO categoryDAO = new CategoryDAO();
    private IGoodDAO goodDAO = new GoodDAO();

    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        try {
            Good good = new Good();

            for(FileItem item: multiparts) {
                String name = item.getFieldName();

                switch (name) {
                    case "name":
                        good.setName(item.getString());
                        break;
                    case "price":
                        good.setPrice(Integer.parseInt(item.getString()));
                        break;
                    case "about":
                        good.setAbout(item.getString());
                        break;
                    case "categoryId":
                        Category category = categoryDAO.readCategory(Integer.parseInt(item.getString()));
                        good.setCategory(category);
                        break;
                }
            }

            good.setPath("images\\goods\\"+String.valueOf(good.hashCode()));

            goodDAO.createGood(good);

            for(FileItem item: multiparts) {
                if(!item.isFormField()) {
                    File uploadedFile = null;
                    //выбираем файлу имя пока не найдём свободное
                    String path = good.getPath();
                    uploadedFile = new File(request.getSession().getServletContext().getRealPath(path));
                    //создаём файл
                    uploadedFile.createNewFile();
                    System.out.println(request.getSession().getServletContext().getRealPath(path));
                    //записываем в него данные
                    item.write(uploadedFile);
                }
            }
        } catch (Exception | DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return new GoodsStartCommand().execute(request);
    }

}
