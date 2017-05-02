package by.bsuir.shop.validator;

import by.bsuir.shop.dao.DAOException;
import by.bsuir.shop.dao.IUserDAO;
import by.bsuir.shop.dao.impl.UserDAO;
import by.bsuir.shop.domain.User;
import by.bsuir.shop.service.ServiceException;
import by.bsuir.shop.util.PasswordEncrypter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * Validator class
 */
public class Validator {
    public static final Logger LOGGER = Logger.getLogger(Validator.class);

    private static int EMPTY_STRING = 0;

    private static int LOGIN_MIN_LENGTH = 4;
    private static int LOGIN_MAX_LENGTH = 15;
    private static int PASSWORD_MIN_LENGTH = 6;
    private static int PASSWORD_MAX_LENGTH = 20;
    private static final String PHONE_NO_REGEXP = "^\\(\\d{3}\\) ?\\d{3}( |-)?\\d{4}|^\\d{3}( |-)?\\d{3}( |-)?\\d{4}";
    private static final int CATEGORY_NAME_MAX_LENGTH = 20;

    private static String LOGIN = "login";
    private static String PASSWORD = "password";
    private static String PASSWORD_REPEAT = "password-repeat";
    private static String PHONE_NO = "phone-no";
    private static String CATEGORY_NAME = "name";

    private static IUserDAO userDAO = new UserDAO();

    private static Set<String> errorSet;

    public static Set<String> getErrorSet() {
        return errorSet;
    }

    public static void setErrorSet(Set<String> errorSet) {
        Validator.errorSet = errorSet;
    }

    public static boolean validateRegisterForm(HttpServletRequest request) throws ServiceException {
        errorSet = new HashSet<>();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        String phoneNo = request.getParameter(PHONE_NO);

        if(login==null || login.length() < LOGIN_MIN_LENGTH || login.length() > LOGIN_MAX_LENGTH) {
            errorSet.add("login.error");
        }
        if(password==null || password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            errorSet.add("password.error");
        }
        if(!password.equals(passwordRepeat)) {
            errorSet.add("password.repeat.error");
        }
        if(phoneNo==null || phoneNo.length()==EMPTY_STRING || !phoneNo.matches(PHONE_NO_REGEXP)) {
            errorSet.add("phone.error");
        }
        try {
            if(userDAO.hasSameLogin(login)) {
                errorSet.add("login.busy.error");
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return errorSet.size()==0;
    }

    public static boolean validateLoginForm(HttpServletRequest request) throws ServiceException {
        errorSet = new HashSet<>();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        if(login==null || login.length() < LOGIN_MIN_LENGTH || login.length() > LOGIN_MAX_LENGTH) {
            errorSet.add("login.error");
        }
        if(password==null || password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            errorSet.add("password.error");
        }
        try {
            User user = new User();
            user.setLogin(login);
            user.setPassword(PasswordEncrypter.encrypt(password));
            if(!userDAO.authorization(user)) {
                errorSet.add("auth.error");
            }
        } catch (DAOException | NoSuchAlgorithmException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }

        return errorSet.size()==0;
    }

    public static boolean validateCategoryForm(HttpServletRequest request) {
        errorSet = new HashSet<>();

        String name = request.getParameter(CATEGORY_NAME);

        if(name==null || name.length()==EMPTY_STRING || name.length() > CATEGORY_NAME_MAX_LENGTH) {
            errorSet.add("name.error");
        }

        return errorSet.size()==0;
    }
}
