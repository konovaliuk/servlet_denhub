package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.dto.MemberDto;
import ua.kpi.pm_system.dto.TaskDto;
import ua.kpi.pm_system.entities.Project;
import ua.kpi.pm_system.entities.TaskPriority;
import ua.kpi.pm_system.entities.TaskStatus;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;
import ua.kpi.pm_system.services.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class TasksCommand implements ICommand{
    private final ProjectService projectService = ServiceFactory.getProjectService();
    private final TeamService teamService = ServiceFactory.getTeamService();
    private final TaskService taskService = ServiceFactory.getTaskService();

    private static final String SIGNIN = "!/../signin";
    private static final String TASKS = "/tasks.jsp";
    private static final String ERROR = "!/../error";
    private static final String RELOAD = "!/tasks?projectId=";
    @Override
    public String execute(HttpServletRequest request) {
        Logger logger = Logger.getLogger("TaskCommand");

        if (request.getSession().getAttribute("currentUser") == null) {
            return SIGNIN;
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        String currentId = request.getParameter("projectId");
        if (currentId == null) {
            return ERROR;
        }
        Project currentProject = null;
        long currentProjectId = Long.parseLong(currentId);
        try {
            currentProject = projectService.findProjectById(currentProjectId);
        } catch (ServiceException e) {e.printStackTrace();}
        if (currentProject == null) {
            return ERROR;
        }
        try {
            if (!projectService.checkMember(currentUser, currentProject)) {
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isManager =  projectService.checkRole(currentUser, currentProject);
        request.setAttribute("isManager", isManager);

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String dateOnly = dateFormat.format(new Date());
        List<MemberDto> currentTeam = teamService.getTeamMembers(currentProject);
        Map<String, List<TaskDto>> taskGrid = taskService.getProjectTasks(currentProject, currentUser, isManager);

        Map<String, String> priorityColors = new HashMap<>();
        priorityColors.put(TaskPriority.LOW.toString(), "#9BE099");
        priorityColors.put(TaskPriority.MEDIUM.toString(), "#DEAE66");
        priorityColors.put(TaskPriority.HIGH.toString(), "#E98074");

        request.setAttribute("currentProject", currentProject);
        request.setAttribute("currentTeam", currentTeam);
        request.setAttribute("currentDate", dateOnly);
        request.setAttribute("taskPriority", TaskPriority.values());
        request.setAttribute("priorityColors", priorityColors);
        request.setAttribute("taskStatus", TaskStatus.values());
        request.setAttribute("taskGrid", taskGrid);

        if ("POST".equals(request.getMethod())) {
            if ("post".equals(request.getParameter("_method"))) {
                String title = request.getParameter("title");
                String instructions = request.getParameter("instructions");
                java.sql.Date deadline = null;
                TaskPriority priority = TaskPriority.valueOf(request.getParameter("priority"));
                long idAssigned = Long.parseLong(request.getParameter("assigned"));

                if (title == null || title.length() < 1) {
                    request.setAttribute("error", "Title should contain at least 1 character.");
                    return TASKS;
                }
                try {
                    deadline = new java.sql.Date(dateFormat.parse(request.getParameter("deadline")).getTime());
                } catch (Exception e) {
                    request.setAttribute("error", "Specify deadline of the task.");
                    return TASKS;
                }
                if (instructions == null) {
                    instructions = "";
                }

                try {
                    taskService.createTask(title, instructions, java.sql.Date.valueOf(dateOnly), deadline, currentProject.getId(),
                            currentUser.getId(), idAssigned, priority);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                return RELOAD + currentProjectId;
            }
            if ("delete".equals(request.getParameter("_method"))){
                long taskId = Long.parseLong(request.getParameter("delete-task"));
                try {
                    taskService.deleteTask(taskId);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }

                return RELOAD + currentProjectId;
            }

        }
        return TASKS;
    }
}
