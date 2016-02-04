/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.constants.PagePath;
import by.pvt.khudnitsky.payments.constants.UserType;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class GoBackAdminCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        UserType userType = RequestParameterParser.getUserType(request);
        if(userType == UserType.ADMINISTRATOR){
            page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
