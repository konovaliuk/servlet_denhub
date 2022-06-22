package ua.kpi.pm_system.services;

public class ServiceFactory {
    private static final UserService userService = new UserService();
    private static final ProjectService projectService = new ProjectService();
    private static final TeamService teamService = new TeamService();
    private static final InvitationService invitationService = new InvitationService();
    private static final TaskService taskService = new TaskService();

    public static UserService getUserService() {
        return userService;
    }
    public static ProjectService getProjectService() {return projectService;}
    public static TeamService getTeamService() {return teamService;}
    public static InvitationService getInvitationService() {return invitationService;}
    public static TaskService getTaskService() {return taskService;}
}
