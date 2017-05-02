package by.bsuir.shop.util;

import by.bsuir.shop.service.*;
import by.bsuir.shop.service.admin.*;
import by.bsuir.shop.service.admin.category.CategoryAddCommand;
import by.bsuir.shop.service.admin.category.CategoryDeleteCommand;
import by.bsuir.shop.service.admin.category.CategoryEditCommand;
import by.bsuir.shop.service.admin.category.CategoryStartCommand;
import by.bsuir.shop.service.admin.good.*;
import by.bsuir.shop.service.admin.order.OrderAcceptCommand;
import by.bsuir.shop.service.admin.order.OrderDeclineCommand;
import by.bsuir.shop.service.admin.order.OrderFilterCommand;
import by.bsuir.shop.service.admin.order.OrderStartCommand;
import by.bsuir.shop.service.admin.user.UserBannCommand;
import by.bsuir.shop.service.admin.user.UserDeleteCommand;
import by.bsuir.shop.service.admin.user.UserRightsCommand;
import by.bsuir.shop.service.admin.user.UserStartCommand;
import by.bsuir.shop.service.cart.*;
import by.bsuir.shop.service.user.*;

/**
 * Command factory method for usual forms
 */
public class CommandUtil {
    public static ICommand getCommand(String name) throws ServiceException {
        CommandEnum command = CommandEnum.getType(name);

        switch (command) {
            case LOGOUT:
                return new LogoutCommand();
            case CART_SUBMIT:
                return new CartSubmitCommand();
            case CART_DELETE:
                return new CartDeleteCommand();
            case CART_EDIT:
                return new CartEditCommand();
            case CART_ADD:
                return new CartAddCommand();
            case CART_FIND:
                return new CartFindCommand();
            case GOODS_START:
                return new GoodsStartCommand();
            case GOODS_ADD:
                return new GoodsAddCommand();
            case GOODS_DELETE:
                return new GoodsDeleteCommand();
            case GOODS_EDIT_START:
                return new GoodsEditStartCommand();
            case GOODS_EDIT:
                return new GoodsEditCommand();
            case ORDER_START:
                return new OrderStartCommand();
            case ORDER_DECLINE:
                return new OrderDeclineCommand();
            case ORDER_ACCEPT:
                return new OrderAcceptCommand();
            case ORDER_FILTER:
                return new OrderFilterCommand();
            case GOODS_CATEGORY_LIST:
                return new GoodsListCategoryCommand();
            case CART_LIST:
                return new CartListCommand();
            case CATEGORY_START:
                return new CategoryStartCommand();
            case CATEGORY_ADD:
                return new CategoryAddCommand();
            case CATEGORY_EDIT:
                return new CategoryEditCommand();
            case CATEGORY_DELETE:
                return new CategoryDeleteCommand();
            case USER_START:
                return new UserStartCommand();
            case USER_BANN:
                return new UserBannCommand();
            case USER_RIGHTS:
                return new UserRightsCommand();
            case USER_DELETE:
                return new UserDeleteCommand();
            case LANGUAGE:
                return new LanguageCommand();
            case LOGIN:
                return new LoginCommand();
            case LOGIN_START:
                return new LoginStartCommand();
            case REGISTER:
                return new RegisterCommand();
            case REGISTER_START:
                return new RegisterStartCommand();
            case NO_COMMAND:
                return new NoCommand();
            default:
                throw new ServiceException();
        }
    }
}
