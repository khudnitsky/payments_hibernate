/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.OperationServiceImpl;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ShowOperationsCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;

        int currentPage = 1;
        int recordsPerPage = 3; //TODO сделать ввод на JSP

        HttpSession session = request.getSession();
        AccessLevelType accessLevelType = RequestParameterParser.getUserType(request);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                if(request.getParameter("page") != null) {
                    currentPage = Integer.parseInt(request.getParameter("page"));
                }
                int numberOfPages = OperationServiceImpl.getInstance().getNumberOfPages(recordsPerPage);
                List<Operation> list = OperationServiceImpl.getInstance().getAllToPage(recordsPerPage, currentPage);
                session.setAttribute(Parameters.OPERATIONS_LIST, list);
                session.setAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
                session.setAttribute(Parameters.CURRENT_PAGE, currentPage);
                page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_SHOW_OPERATIONS_PAGE);
            }
            catch (ServiceException e) {
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
