package Databases;

import Model.User;

import java.sql.*;

public class UserDB {
    private Connection conn;
    private Statement s;

    public UserDB() {}

    /**
     * Sends a select query to the database.
     * @return the result from the database
     */
    public User select(String email, String password) throws SQLException {
        String str = "SELECT * FROM UsersDB WHERE email = \""+email+"\" AND password = \""+password+"\";";
        ResultSet rs = s.executeQuery(str);
        if (rs.next()) {
            return new User(rs.getString("name"),rs.getString("email"),rs.getString("password"),rs.getBoolean("moderator"));
        }
        return null;
    }

    /**
     * Sends an insert query to the database.
     */
    public void insert(User user) throws SQLException {
        String str = "INSERT INTO UsersDB VALUES (?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.setString(1,user.getName());
        ps.setString(2,user.getEmailAddress());
        ps.setString(3,user.getPassword());
        ps.setString(4,"0");
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Checks if the name is in the database already.
     * @return true if the name exists
     */
    public boolean checkName(String name) throws SQLException {
        String str = "SELECT * FROM UsersDB WHERE name = \""+name+"\";";
        ResultSet rs = s.executeQuery(str);
        return rs.next();
    }

    /**
     * Creates the connection to the database.
     */
    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        s = conn.createStatement();
    }

    /**
     * Removes the connection to the database.
     */
    public void disconnect() throws SQLException {
        conn.close();
        s.close();
    }
}
