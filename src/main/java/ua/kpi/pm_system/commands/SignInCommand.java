package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;
import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignInCommand implements ICommand {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            return "/signIn.jsp";
        }
        else {
            try {
                User currentUser = userService.signInUser(request.getParameter("username"), request.getParameter("password"));
                request.getSession().setAttribute("currentUser", currentUser);
                return "!/profile";
            }
            catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
                return "/signIn.jsp";
            }
        }
    }
}
