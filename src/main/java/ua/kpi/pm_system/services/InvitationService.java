package ua.kpi.pm_system.services;

import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.IInvitationDao;
import ua.kpi.pm_system.dao.interfaces.IProjectDao;
import ua.kpi.pm_system.dao.interfaces.ITeamDao;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.dto.InvitationDto;
import ua.kpi.pm_system.entities.*;
import ua.kpi.pm_system.exceptions.ServiceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitationService {
    private final IInvitationDao invitationDao = DaoFactory.getInvitationDao();
    private final IUserDao userDao = DaoFactory.getUserDao();
    private final ITeamDao teamDao = DaoFactory.getTeamDao();
    private final IProjectDao projectDao = DaoFactory.getProjectDao();

    public void createInvitation(User currentUser, Project currentProject, String usernameInvited) throws ServiceException {
        User invitedUser = null;
        if (currentUser.getId() != currentProject.getOwnerId()) {
            throw new ServiceException("You don't have permission to perform this action.");
        }
        try {
            invitedUser = userDao.findByUsername(usernameInvited);
        }
        catch (SQLException e) {e.printStackTrace();}

        if (invitedUser == null) {
            throw new ServiceException("User with username \"" + usernameInvited + "\" doesn't exist");
        }
        if (currentUser.getId() == invitedUser.getId()) {
            throw new ServiceException("This user already the part of the project or invited");
        }

        Team member = new Team(currentProject.getId(), invitedUser.getId());
        try {
            ProjectRole role = teamDao.checkMember(member);
            if (role != null) {
                throw new ServiceException("This user already the part of the project");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Invitation invitation = null;
        try {
            invitation = invitationDao.findInvitationByProjectIdUserId(invitedUser.getId(), currentUser.getId(), currentProject.getId());

        } catch (SQLException e) {e.printStackTrace();}

        if (invitation != null){
            if (invitation.getInvitationStatus() == InvitationStatus.NOT_PROCEED) {
                throw new ServiceException("This user already invited to the project.");
            }
        }

        try {
            invitationDao.create(invitedUser.getId(), currentUser.getId(), currentProject.getId());
        } catch (SQLException e) {e.printStackTrace();}
    }

    public List<InvitationDto> findUserInvitations(User currentUser) {
        List<InvitationDto> invitations = new ArrayList<>();
        List<Invitation> invitationList = new ArrayList<>();
        try {
            invitationList = invitationDao.findUserInvitations(currentUser.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Invitation invitation: invitationList) {
            try {
                String username = userDao.findById(invitation.getIdInviter()).getUsername();
                String projectTitle = projectDao.findProjectById(invitation.getIdProject()).getTitle();
                invitations.add(new InvitationDto(username, projectTitle, invitation.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return invitations;
    }

    public void updateInvitationStatus(long invitationId, InvitationStatus invitationStatus) throws ServiceException{
        Invitation invitation = null;
        try {
            invitation = invitationDao.findInvitationById(invitationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (invitation == null) {
            throw new ServiceException("Invitation with id=" + invitationId + " doesn't exist.");
        }
        try {
            invitationDao.changeStatus(invitation, invitationStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (invitationStatus == InvitationStatus.CONFIRMED) {
            try {
                teamDao.addMember(invitation.getIdProject(), invitation.getIdInvited());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

