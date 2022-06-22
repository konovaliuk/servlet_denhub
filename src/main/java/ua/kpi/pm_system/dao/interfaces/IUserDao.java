package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.User;

import java.sql.SQLException;

public interface IUserDao {

    User findById(long id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    User create(String email, String username, String passwordhash, String firstname, String lastname, String bio) throws SQLException;
    void update(User user) throws SQLException;
    void delete(User user) throws SQLException;

}
