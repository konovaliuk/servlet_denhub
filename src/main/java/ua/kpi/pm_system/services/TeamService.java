package ua.kpi.pm_system.services;

import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.ITaskDao;
import ua.kpi.pm_system.dao.interfaces.ITeamDao;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.dto.MemberDto;
import ua.kpi.pm_system.entities.*;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TeamService {
    private final IUserDao userDao = DaoFactory.getUserDao();
    private final ITeamDao teamDao = DaoFactory.getTeamDao();
    private final ITaskDao taskDao = DaoFactory.getTaskDao();

    public List<MemberDto> getTeamMembers(Project currentProject) {
        List<MemberDto> membersUsernames = new ArrayList<>();
        try {
            List<Team> members = teamDao.findProjectMembers(currentProject.getId());
            for (Team member: members) {
                membersUsernames.add(new MemberDto(userDao.findById(member.getUserId()).getUsername(), member.getProjectRole() == ProjectRole.MANAGER, member.getUserId(), member.getProjectId()));
            }
            return membersUsernames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membersUsernames;
    }

    public void changeRole(long idUser, long idProject, boolean isManager) {
        ProjectRole projectRole = ProjectRole.EMPLOYEE;
        if (isManager) {
            projectRole = ProjectRole.MANAGER;
        }
        try {
            teamDao.changeRole(new Team(idProject, idUser), projectRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMember(long idUser, Project currentProject) {
        Logger logger = Logger.getLogger("logger");
        Team member = new Team(currentProject.getId(), idUser);
        try {
            List<Task> userTasks = taskDao.userProjectTasks(currentProject.getId(), idUser);
            for (Task task: userTasks) {
                if (task.getIdCreator() == idUser) {
                    logger.info("aaaaaaa");
                    task.setIdCreator(currentProject.getOwnerId());
                }
                if (task.getIdAssigned() == idUser) {
                    task.setIdAssigned(task.getIdCreator());
                }
                taskDao.update(task);
            }
            teamDao.delete(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
