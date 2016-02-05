/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.constants.PagePath;
import by.pvt.khudnitsky.payments.constants.AccessLevel;
import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class GoToAddFundsCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        AccessLevel accessLevel = RequestParameterParser.getUserType(request);
        if(accessLevel == AccessLevel.CLIENT){
            page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
