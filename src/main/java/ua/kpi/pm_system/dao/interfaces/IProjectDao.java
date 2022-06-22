package ua.kpi.pm_system.dao.interfaces;

import ua.kpi.pm_system.entities.Project;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IProjectDao {
    Project findProjectById(long id) throws SQLException;
    List<Project> findUserOwnProjects(long userId) throws SQLException;
    Project create(String title, String description, Date dateStart, Date dateFinish, long ownerId) throws SQLException;
    void update(Project project) throws SQLException;
    void delete(Project project) throws SQLException;
}
