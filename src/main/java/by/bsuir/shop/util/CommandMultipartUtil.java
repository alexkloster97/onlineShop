package by.bsuir.shop.util;

import by.bsuir.shop.service.ICommand;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.service.admin.AbstractFileUploadCommand;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command factory method for multipart forms
 */
public class CommandMultipartUtil {
    public static final Logger LOGGER = Logger.getLogger(CommandMultipartUtil.class);

    public static ICommand getCommand(HttpServletRequest request) throws ServiceException {
        try {
            List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            for(FileItem item: multiparts) {
                String name = item.getFieldName();

                if("command".equals(name)){
                    ICommand command = CommandUtil.getCommand(item.getString());
                    ((AbstractFileUploadCommand) command).setMultiparts(multiparts);
                    return command;
                }
            }
        } catch (FileUploadException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        throw new ServiceException("No suitable command found");
    }
}
