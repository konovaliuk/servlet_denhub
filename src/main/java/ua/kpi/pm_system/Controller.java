package ua.kpi.pm_system;

import ua.kpi.pm_system.commands.ICommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name="Controller", urlPatterns = {"/signup", "/signin", "/profile", "/projects", "/project", "/project/tasks", "/project/tasks/task", "/error", "/logout"})
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        processRequest(request, response);

    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ICommand command = ControllerHelper.getInstance().getCommand(request);
        String page = command.execute(request);
        if (page.startsWith("!")) {
            response.sendRedirect(page.substring(2));
        }
        else {
            request.getServletContext().getRequestDispatcher(page).forward(request, response);
        }
    }
}