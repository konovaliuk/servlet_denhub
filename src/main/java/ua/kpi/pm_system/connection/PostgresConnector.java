package ua.kpi.pm_system.connection;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PostgresConnector {
    private static BasicDataSource dataSource;
    private PostgresConnector() {
    }

    private static void initDataSource() {
        ResourceBundle resource = ResourceBundle.getBundle("postgres");
        String url = resource.getString("url");
        String username = resource.getString("username");
        String password = resource.getString("password");
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxTotal(5);
    }

    public static Connection getConnection() throws SQLException{
        Connection connection;

        if (dataSource == null) {
            initDataSource();
        }

        connection = dataSource.getConnection();

        return connection;
    }
}
