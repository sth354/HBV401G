package Databases;

import Model.User;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    Connection conn;

    public UserDB() throws SQLException, ClassNotFoundException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
    }

    public boolean select(User username) throws SQLException, ParseException {
        List<User> out = new ArrayList<User>();

        Statement s = conn.createStatement();
        String str = "SELECT * FROM UsersDB;";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            User outD = new User(rs.getString("name"),rs.getString("emailAddress"),rs.getString("password"),rs.getBoolean("moderator"));
            if (username.equals(outD))
                return true;
        }
        return false;
    }
}
