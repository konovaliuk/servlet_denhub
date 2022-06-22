package ua.kpi.pm_system.commands;

import ua.kpi.pm_system.dto.MemberDto;
import ua.kpi.pm_system.entities.Project;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;
import ua.kpi.pm_system.services.InvitationService;
import ua.kpi.pm_system.services.ProjectService;
import ua.kpi.pm_system.services.ServiceFactory;
import ua.kpi.pm_system.services.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

public class ProjectPageCommand implements ICommand{
    private final ProjectService projectService = ServiceFactory.getProjectService();
    private final TeamService teamService = ServiceFactory.getTeamService();
    private final InvitationService invitationService = ServiceFactory.getInvitationService();

    private static final String SIGNIN = "!/signin";
    private static final String PROJECT = "/project.jsp";
    private static final String ERROR = "!/error";
    private static final String RELOAD = "!/project";
    private static final String PROJECTS = "!/projects";

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("currentUser") == null) {
            return SIGNIN;
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        Project currentProject = null;

        String currentId = request.getParameter("id");
        if (currentId == null) {
            return ERROR;
        }
        try {
            currentProject = projectService.findProjectById(Long.parseLong(currentId));

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
        List<MemberDto> currentTeam = teamService.getTeamMembers(currentProject);
        request.setAttribute("currentProject", currentProject);
        request.setAttribute("currentTeam", currentTeam);

        if ("POST".equals(request.getMethod())) {
            if ("post".equals(request.getParameter("_method"))) {
                try {
                    invitationService.createInvitation(currentUser, currentProject, request.getParameter("username"));
                } catch (ServiceException e) {
                    request.setAttribute("error", e);
                }
                return RELOAD + "?id=" + currentProject.getId();
            }
            if ("put".equals(request.getParameter("_method"))) {
                String[] changeRole = request.getParameter("changeRole").split(":");
                long idUser = Long.parseLong(changeRole[0]);
                long idProject = Long.parseLong(changeRole[1]);
                boolean newRole = Boolean.parseBoolean(changeRole[2]);
                if (idUser != currentProject.getOwnerId()) {
                    teamService.changeRole(idUser, idProject, newRole);
                }
                return RELOAD + "?id=" + currentProject.getId();
            }
            if ("patch".equals(request.getParameter("_method"))) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                Date dateStart;
                Date dateFinish;

                if (title == null) {
                    request.setAttribute("error", "Title should contain at least 1 character.");
                    return PROJECT;
                }

                DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateStart = new Date(simpleDateFormat.parse(request.getParameter("dateStart")).getTime());
                } catch (Exception e) {
                    request.setAttribute("error", "Specify the start of the project.");
                    return PROJECT;
                }
                try {
                    dateFinish = new Date(simpleDateFormat.parse(request.getParameter("dateFinish")).getTime());
                } catch (Exception e) {
                    request.setAttribute("error", "Specify the end of the project.");
                    return PROJECT;
                }

                if (description == null) {
                    description = "";
                }
                if (dateStart == null) {
                    request.setAttribute("error", "Specify the start of the project.");
                    return PROJECT;
                }
                if (dateFinish == null) {
                    request.setAttribute("error", "Specify the end of the project.");
                    return PROJECT;
                }
                if (dateStart.compareTo(dateFinish) >= 0) {
                    request.setAttribute("error", "Invalid time interval.");
                    return PROJECT;
                }

                boolean allowAll = Boolean.parseBoolean(request.getParameter("allowAll"));
                boolean showAll = Boolean.parseBoolean(request.getParameter("showAll"));

                projectService.updateProject(currentProject, title, description, dateStart, dateFinish, allowAll, showAll);

                return RELOAD + "?id=" + currentProject.getId();
            }

            if ("delete".equals(request.getParameter("_method"))) {
                String[] deleteMember = request.getParameter("deleteMember").split(":");
                long idUser = Long.parseLong(deleteMember[0]);
                teamService.removeMember(idUser, currentProject);

                if (idUser == currentUser.getId()) {
                    return PROJECTS;
                }
                return RELOAD + "?id=" + currentProject.getId();
            }
        }
        return PROJECT;
    }
}
