package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.dto.MemberDto;
import ua.kpi.pm_system.dto.TaskDto;
import ua.kpi.pm_system.entities.Project;
import ua.kpi.pm_system.entities.TaskPriority;
import ua.kpi.pm_system.entities.TaskStatus;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;
import ua.kpi.pm_system.services.ProjectService;
import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.TaskService;
import ua.kpi.pm_system.services.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditTaskCommand implements ICommand{
    private final static String TASK = "/editTask.jsp";
    private final static String TASKS = "!/../tasks";
    private final static String ERROR = "!/../../error";
    private final static String SIGNIN = "!/../../signin";

    private final ProjectService projectService = ServiceFactory.getProjectService();
    private final TeamService teamService = ServiceFactory.getTeamService();
    private final TaskService taskService = ServiceFactory.getTaskService();
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("currentUser") == null) {
            return SIGNIN;
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        String projectId = request.getParameter("projectId");
        if (projectId == null) {
            return ERROR;
        }
        Project currentProject = null;
        long currentProjectId = Long.parseLong(projectId);
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

        String taskId = request.getParameter("taskId");
        if (taskId == null) {
            return ERROR;
        }
        TaskDto currentTask = taskService.getTaskById(Long.parseLong(taskId));
        if (currentTask == null) {
            return ERROR;
        }
        if (currentTask.getIdCreator() != currentUser.getId() && currentProject.getOwnerId() != currentUser.getId() && currentTask.getIdAssigned() != currentUser.getId()) {
            return ERROR;
        }
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        if ("GET".equals(request.getMethod())) {

            String dateOnly = dateFormat.format(new Date());
            List<MemberDto> currentTeam = teamService.getTeamMembers(currentProject);
            request.setAttribute("currentTask", currentTask);
            request.setAttribute("currentTeam", currentTeam);
            request.setAttribute("currentDate", dateOnly);
            request.setAttribute("taskPriority", TaskPriority.values());
            request.setAttribute("taskStatus", TaskStatus.values());
            request.setAttribute("projectId", currentProjectId);
            request.setAttribute("currentProject", currentProject);
            return TASK;
        }

        else {
            String title = request.getParameter("title");
            String instructions = request.getParameter("instructions");
            java.sql.Date deadline = null;
            TaskPriority priority = TaskPriority.valueOf(request.getParameter("priority"));
            TaskStatus status= TaskStatus.valueOf(request.getParameter("status"));
            long idAssigned = Long.parseLong(request.getParameter("assigned"));

            if (title == null || title.length() < 1) {
                request.setAttribute("error", "Title should contain at least 1 character.");
                return TASK;
            }
            try {
                deadline = new java.sql.Date(dateFormat.parse(request.getParameter("deadline")).getTime());
            } catch (Exception e) {
                request.setAttribute("error", "Specify deadline of the task.");
                return TASK;
            }
            if (instructions == null) {
                instructions = "";
            }
            try {
                taskService.updateTask(Long.parseLong(taskId), title, instructions, deadline, idAssigned, priority, status);
            } catch (ServiceException e) {
                request.setAttribute("error", e.getMessage());
            }
            return TASKS + "?projectId=" + projectId ;
        }
    }
}
