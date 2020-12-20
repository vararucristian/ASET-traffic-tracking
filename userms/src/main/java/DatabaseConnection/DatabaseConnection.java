package DatabaseConnection;

import Data.User;
import Response.ResponseTemplate;

import java.sql.*;

public class DatabaseConnection {
    public static DatabaseConnection instance = null;

    private Connection conn;

    private DatabaseConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/UsersDB";
        conn = DriverManager.getConnection(url, "postgres", "mango");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            try {
                instance = new DatabaseConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return instance;
    }

    public int getUsersId() {
        int id = 0;
        try {
            PreparedStatement statement = conn.prepareStatement("select count(*) from users");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id + 1;
    }

    public Boolean insertUser(String fname, String lname, String password, String username) {
        try {
            PreparedStatement statement = conn.prepareStatement("insert into users(id, fname, lname, username, password) values(?, ?, ?, ?, ?)");
            statement.setInt(1, getUsersId());
            statement.setString(2, fname);
            statement.setString(3, lname);
            statement.setString(4, username);
            statement.setString(5, password);
            statement.executeUpdate();
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public ResponseTemplate authenticateUser(String password, String username) {
        ResponseTemplate response = new ResponseTemplate("", "", "", false);
        try {
            PreparedStatement statement = conn.prepareStatement("select fname, lname, username from users where username = ? and password=?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                response = new ResponseTemplate(rs.getString("fname"),
                                                rs.getString("lname"),
                                                rs.getString("username"),
                                         true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return response;
    }

    public User getUserByUsername(String username) {
        try {
            PreparedStatement statement = conn.prepareStatement("select * from users where username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("fname"), rs.getString("lname"), rs.getInt("id"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }

}
