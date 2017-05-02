package by.bsuir.shop.service.admin;

import org.apache.commons.fileupload.FileItem;

import java.util.List;

/**
 * Abstract file upload command, non-executable
 */
public abstract class AbstractFileUploadCommand {
    protected List<FileItem> multiparts;

    public List<FileItem> getMultiparts() {
        return multiparts;
    }

    public void setMultiparts(List<FileItem> multiparts) {
        this.multiparts = multiparts;
    }
}
