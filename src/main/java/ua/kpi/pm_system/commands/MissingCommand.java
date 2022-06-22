package ua.kpi.pm_system.commands;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class MissingCommand implements ICommand{
    private final static String ERROR = "/error.jsp";
    @Override
    public String execute(HttpServletRequest request) {
        return ERROR;
    }
}
