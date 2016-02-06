/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class AddFundsCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        AccessLevelEnum accessLevelEnum = RequestParameterParser.getUserType(request);
        if(accessLevelEnum == AccessLevelEnum.CLIENT) {
            User user = RequestParameterParser.getRecordUser(request);
            try {
                if (!AccountServiceImpl.getInstance().checkAccountStatus(user.getAccountId())) {
                    double amount = RequestParameterParser.getAmountFromFunds(request);
                    if (amount > 0) {
                        CommandType type = RequestParameterParser.getCommandType(request);
                        String description = type.getValue();
                        AccountServiceImpl.getInstance().addFunds(user, description, amount);
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                        page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);
                    } else {
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);
                    }
                } else {
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException | SQLException e) {
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            } catch (NumberFormatException e) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_ADDFUNDS_PAGE_PATH);

            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
