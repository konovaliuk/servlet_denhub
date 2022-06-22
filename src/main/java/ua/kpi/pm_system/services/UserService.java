package ua.kpi.pm_system.services;

import ua.kpi.pm_system.Utils;
import ua.kpi.pm_system.dao.DaoFactory;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.entities.User;
import ua.kpi.pm_system.exceptions.ServiceException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class UserService {
    private final IUserDao userDao = DaoFactory.getUserDao();

    public User signUpUser(String email, String username,  String password, String firstName, String lastName, String bio) throws ServiceException {
        User currentUser;
        try {
            currentUser = userDao.findByUsername(username);
        } catch (Exception e) {throw new ServiceException("Something go wrong.");}

        if (currentUser != null) {
            throw new ServiceException("Username " + username + " is already taken.");
        }
        try {
            currentUser = userDao.create(email, username, Utils.hashPassword(password), firstName, lastName, bio);
        } catch (Exception e) {throw new ServiceException("Something go wrong.");}

        return currentUser;
    }

    public User signInUser(String username, String password) throws ServiceException {
        User currentUser;
        try {
            currentUser = userDao.findByUsername(username);
        } catch (Exception e) {
            throw new ServiceException("Something go wrong");
        }

        if (currentUser == null) {
            throw new ServiceException("Username " + username + " doesn't exist.");
        }
        if (!Objects.equals(currentUser.getPasswordHash(), Utils.hashPassword(password))) {
            throw new ServiceException("Invalid password.");
        }
        return currentUser;
    }

    public void logoutUser() {

    }

    public User updateUser(String firstName, String lastName, String bio, User user) throws ServiceException{
        if (lastName == null) {
            lastName = "";
        }
        if (bio == null) {
            bio = "";
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBio(bio);
        try {
            userDao.update(user);
        }
        catch (SQLException e) {
            throw new ServiceException("Something go wrong.");
        }

        return user;
    }

}
