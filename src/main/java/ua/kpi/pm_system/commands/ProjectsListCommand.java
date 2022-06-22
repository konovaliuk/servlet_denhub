package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.dto.InvitationDto;
import ua.kpi.pm_system.entities.InvitationStatus;
import ua.kpi.pm_system.entities.Project;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;
import ua.kpi.pm_system.services.InvitationService;
import ua.kpi.pm_system.services.ProjectService;
import ua.kpi.pm_system.services.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProjectsListCommand implements ICommand{
    private final ProjectService projectService = ServiceFactory.getProjectService();
    private final InvitationService invitationService = ServiceFactory.getInvitationService();
    private static final String PROJECTS = "/projects.jsp";
    private static final String SIGNIN = "!/signin";
    private static final String PROJECT = "!/project?id=";
    private static final String RELOAD = "!/projects";


    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("currentUser") == null) {
            return SIGNIN;
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        try{
            List<Project> projects = projectService.findAllUserProjects(currentUser);
            List<List<Project>> projectGrid= new ArrayList<>();
            int row = 1;
            int currColumn = 1;
            int maxColumns = 3;
            List<Project> projectRow = new ArrayList<>();
            for (Project project: projects) {
                if (currColumn > maxColumns) {
                    projectGrid.add(projectRow);
                    projectRow = new ArrayList<>();
                    currColumn = 1;
                    row += 1;
                }
                projectRow.add(project);

                if ((3 * Math.min(1, row - 1)+ Math.max(row - 2, 0) * 4 + currColumn) >= projects.size()) {
                    projectGrid.add(projectRow);
                    break;
                }

                if (row == 2) {
                    maxColumns = 4;
                }
                currColumn += 1;
            }
            if (projectGrid.size() == 0) {
                projectGrid.add(new ArrayList<>());
            }

            int lastRowSize = projectGrid.get(projectGrid.size() - 1).size();
            if (row > 1) {
                maxColumns = 4;
            }
            if (lastRowSize < maxColumns) {
                for (int i = 0; i < maxColumns - lastRowSize; i++) {
                    projectGrid.get(projectGrid.size() - 1).add(null);
                }
            }
            maxColumns = 4;
            if (projectGrid.size() % 2 != 0) {
                List<Project> nullRow = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    nullRow.add(null);
                }
                projectGrid.add(nullRow);
            }
            request.setAttribute("projectGrid", projectGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger logger = Logger.getLogger("logger");
        List<InvitationDto> invitations = invitationService.findUserInvitations(currentUser);
        request.setAttribute("invitations", invitations);
        /*logger.info(String.valueOf(invitations.size()));*/

        if ("POST".equals(request.getMethod())){
            if ("post".equals(request.getParameter("_method"))) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                Date dateStart;
                Date dateFinish;

                if (title == null) {
                    request.setAttribute("error", "Title should contain at least 1 character.");
                    return PROJECTS;
                }

                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateStart = new Date(simpleDateFormat.parse(request.getParameter("dateStart")).getTime());
                } catch (Exception e) {
                    request.setAttribute("error", "Specify the start of the project.");
                    return PROJECTS;
                }
                try {
                    dateFinish = new Date(simpleDateFormat.parse(request.getParameter("dateFinish")).getTime());
                } catch (Exception e) {
                    request.setAttribute("error", "Specify the end of the project.");
                    return PROJECTS;
                }

                if (description == null) {
                    description = "";
                }
                if (dateStart == null) {
                    request.setAttribute("error", "Specify the start of the project.");
                    return PROJECTS;
                }
                if (dateFinish == null) {
                    request.setAttribute("error", "Specify the end of the project.");
                    return PROJECTS;
                }
                if (dateStart.compareTo(dateFinish) >= 0) {
                    request.setAttribute("error", "Invalid time interval.");
                    return PROJECTS;
                }
                try {
                    Project project = projectService.createProject(title, description, dateStart, dateFinish, (User) request.getSession().getAttribute("currentUser"));
                    return PROJECT + project.getId();
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            if ("put".equals(request.getParameter("_method"))){
                String[] action = request.getParameter("join").split(":");
                long invitationId = Long.parseLong(action[0]);
                InvitationStatus status = InvitationStatus.valueOf(action[1]);
                try {
                    invitationService.updateInvitationStatus(invitationId, status);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                logger.info(request.getParameter("join"));
                return RELOAD;
            }
        }
        return PROJECTS;
    }
}