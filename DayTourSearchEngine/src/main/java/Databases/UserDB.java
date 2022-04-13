package Databases;

import Model.User;

import java.sql.*;

public class UserDB {
    private Connection conn;

    public UserDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        }
        catch (SQLException ignored) {}
    }

    public User select(String email, String password) throws SQLException {
        Statement s = conn.createStatement();
        String str = "SELECT * FROM UsersDB WHERE email = \""+email+"\" AND password = \""+password+"\";";
        ResultSet rs = s.executeQuery(str);
        if (rs.next()) {
            return new User(rs.getString("name"),rs.getString("email"),rs.getString("password"),rs.getBoolean("moderator"));
        }
        return null;
    }

    public boolean checkName(String name) throws SQLException {
        Statement s = conn.createStatement();
        String str = "SELECT * FROM UsersDB WHERE name = \""+name+"\";";
        ResultSet rs = s.executeQuery(str);
        return rs.next();
    }
}
