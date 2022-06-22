package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.Utils;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ProfilePageCommand implements ICommand{
    private final UserService userService = ServiceFactory.getUserService();
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("currentUser") == null) {
            return "!/signin";
        }
        if ("GET".equals(request.getMethod())) {
            request.setAttribute("edit", false);

            if (request.getParameter("edit") != null) {
                request.setAttribute("edit", true);
            }
        }

        else {
            try {
                User currentUser = userService.updateUser(Utils.validateFirstName(request.getParameter("firstName")), request.getParameter("lastName"),
                        request.getParameter("bio"), (User) request.getSession().getAttribute("currentUser"));
                request.getSession().setAttribute("currentUser", currentUser);
            }
            catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
            return "!/profile";
        }
        return "/profile.jsp";
    }
}
