package by.bsuir.shop.service.admin.category;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.ICategoryDAO;
import by.bsuir.shop.dao.impl.CategoryDAO;
import by.bsuir.shop.domain.Category;
import by.bsuir.shop.domain.Good;
import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.service.admin.AbstractFileUploadCommand;
import by.bsuir.shop.service.admin.good.GoodsStartCommand;
import by.bsuir.shop.validator.Validator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Add new category command
 */
public class CategoryAddCommand extends AbstractFileUploadCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CategoryAddCommand.class);
    private ICategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Process request
     * @param request           request to process
     * @return                  name of new view
     * @throws ServiceException
     */
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {

        try {
            Category category = new Category();

            for(FileItem item: multiparts) {
                String name = item.getFieldName();

                switch (name) {
                    case "name":
                        category.setName(item.getString());
                        break;
                }
            }

            category.setPath("images\\categories\\"+String.valueOf(category.hashCode()));

            categoryDAO.createCategory(category);

            for(FileItem item: multiparts) {
                if(!item.isFormField()) {
                    File uploadedFile = null;
                    //выбираем файлу имя пока не найдём свободное
                    String path = category.getPath();
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

        return new CategoryStartCommand().execute(request);
    }
}
