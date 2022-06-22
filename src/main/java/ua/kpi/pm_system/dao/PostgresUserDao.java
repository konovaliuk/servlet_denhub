package ua.kpi.pm_system.dao;

import ua.kpi.pm_system.connection.PostgresConnector;
import ua.kpi.pm_system.dao.interfaces.IUserDao;
import ua.kpi.pm_system.entities.User;

import java.sql.*;

public class PostgresUserDao implements IUserDao {
    private final String tableName = "\"user\".\"user\"";

    private final String ID = "id";
    private final String EMAIL = "email";
    private final String USERNAME = "username";
    private final String PASSWORD_HASH = "passwordHash";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String BIO = "bio";

    private final String UPDATE = "update " + tableName + " set " + EMAIL + "=?, " + PASSWORD_HASH + "=?, " +
                                    FIRST_NAME + "=?, " + LAST_NAME + "=?, " + BIO + "=?";

    public PostgresUserDao(){

    }

    private User getUser(ResultSet rs) throws  SQLException{
        long id = rs.getLong(ID);
        String email = rs.getString(EMAIL);
        String username = rs.getString(USERNAME);
        String passwordHash = rs.getString(PASSWORD_HASH);
        String firstName = rs.getString(FIRST_NAME);
        String lastName = rs.getString(LAST_NAME);
        String bio = rs.getString(BIO);
        return new User(id, username, email, passwordHash, firstName, lastName, bio);
    }

    @Override
    public User findById(long id) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + ID + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()){
            user = getUser(rs);
        }

        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        Connection connection = PostgresConnector.getConnection();
        String query = "select * from " + tableName + " where " + USERNAME + "=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()){
            user = getUser(rs);
        }

        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    @Override
    public User create(String email, String username, String passwordHash, String firstName, String lastName, String bio) throws SQLException {
        Connection connection = PostgresConnector.getConnection();

        String fields = EMAIL + "," + USERNAME + "," + PASSWORD_HASH + "," + FIRST_NAME + "," +  LAST_NAME + "," + BIO;
        String command = "insert into " + tableName + "(" + fields + ") " + "values(?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, email);
        ps.setString(2, username);
        ps.setString(3, passwordHash);
        ps.setString(4, firstName);
        ps.setString(5, lastName);
        ps.setString(6, bio);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        User user = null;

        if (rs.next()) {
            long newId = rs.getLong(1);
            user = new User(newId, username, email, passwordHash, firstName, lastName, bio);
        }

        rs.close();
        ps.close();
        connection.close();

        return user;
    }

    public void update(User user) throws SQLException{
        Connection connection = PostgresConnector.getConnection();
        PreparedStatement ps = connection.prepareStatement(UPDATE + " where " + ID + "=?");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPasswordHash());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setString(5, user.getBio());
        ps.setLong(6, user.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void delete(User user) throws SQLException{
        Connection connection = PostgresConnector.getConnection();

        String command = "delete from " + tableName + " where " + ID + "=?";
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setLong(1, user.getId());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

}
