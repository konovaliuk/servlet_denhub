package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.Utils;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignUpCommand implements ICommand{
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request) {
        if ("GET".equals(request.getMethod())) {
            return "/signUp.jsp";
        }
        else {
            try {
                User currentUser = userService.signUpUser(Utils.validateEmail(request.getParameter("email")),
                        Utils.validateUsername(request.getParameter("username")),
                        Utils.validatePassword(request.getParameter("password")),
                        Utils.validateFirstName(request.getParameter("firstName")),
                        request.getParameter("lastName"), request.getParameter("bio"));
                return "!/signin";
            }
            catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                return "/signUp.jsp";
            }
        }
    }
}
