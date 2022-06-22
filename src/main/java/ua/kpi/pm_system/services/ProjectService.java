package ua.kpi.pm_system.services;

import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.IProjectDao;
import ua.kpi.pm_system.dao.interfaces.ITeamDao;
import ua.kpi.pm_system.entities.Project;
import ua.kpi.pm_system.entities.ProjectRole;
import ua.kpi.pm_system.entities.Team;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private final IProjectDao projectDao = DaoFactory.getProjectDao();
    private final ITeamDao teamDao = DaoFactory.getTeamDao();

    public List<Project> findAllUserProjects(User user) throws ServiceException {
        List<Team> projectsId;
        List<Project> projects;
        try {
            projectsId = teamDao.findUserAllProjects(user.getId());
            projects = new ArrayList<>();
            for (Team team : projectsId) {
                projects.add(projectDao.findProjectById(team.getProjectId()));
            }
        } catch (Exception e) {
            throw new ServiceException("Something go wrong");
        }
        
        return projects;
    }

    public Project createProject(String title, String description, Date dateStart, Date dateFinish, User currentUser) throws ServiceException{
        try {
            Project project = projectDao.create(title, description, dateStart, dateFinish, currentUser.getId());
            teamDao.addMember(project.getId(), currentUser.getId(), ProjectRole.MANAGER);
            return project;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("Something go wrong");
        }
    }

    public void updateProject(Project currentProject, String title, String description, Date dateStart, Date dateFinish,
                              boolean allowAll, boolean showAll) {
        currentProject.setTitle(title);
        currentProject.setDescription(description);
        currentProject.setDateStart(dateStart);
        currentProject.setDateFinish(dateFinish);
        currentProject.setAllowAll(allowAll);
        currentProject.setShowAll(showAll);

        try {
            projectDao.update(currentProject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project findProjectById(long id) throws ServiceException{
        try {
            return projectDao.findProjectById(id);
        } catch (SQLException e) {throw new ServiceException((e.getMessage()));}

    }

    public boolean checkRole(User user, Project project) {
        Team member = new Team(project.getId(), user.getId());
        try {
            ProjectRole role = teamDao.checkMember(member);
            if (role == null) {
                /*throw new ServiceException("You're not allowed to perform this action");*/
                return false;
            }
            if (role == ProjectRole.MANAGER) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkMember(User user, Project project) {
        Team member = new Team(project.getId(), user.getId());
        try {
            ProjectRole role = teamDao.checkMember(member);
            if (role == null) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
