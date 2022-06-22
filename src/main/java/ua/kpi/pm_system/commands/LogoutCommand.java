package ua.kpi.pm_system.commands;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ICommand{
    private final static String SIGNIN = "!/signin";
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return SIGNIN;
    }
}
