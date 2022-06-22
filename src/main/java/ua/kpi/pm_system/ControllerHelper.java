package ua.kpi.pm_system;

import ua.kpi.pm_system.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ControllerHelper {
    private static ControllerHelper instance;
    private static HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put("/signup", new SignUpCommand());
        commands.put("/signin", new SignInCommand());
        commands.put("/profile", new ProfilePageCommand());
        commands.put("/projects", new ProjectsListCommand());
        commands.put("/project", new ProjectPageCommand());
        commands.put("/project/tasks", new TasksCommand());
        commands.put("/project/tasks/task", new EditTaskCommand());
        commands.put("/error", new MissingCommand());
        commands.put("/logout", new LogoutCommand());

    }

    public ICommand getCommand(HttpServletRequest req) {
        if(!commands.containsKey(req.getRequestURI())) {
            return new MissingCommand();
        }
        return commands.get(req.getRequestURI());
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }

        return instance;
    }
}
