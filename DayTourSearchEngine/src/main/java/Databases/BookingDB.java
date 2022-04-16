package Databases;

import Model.DayTour;
import Model.User;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private Connection conn;
    private Statement s;
    private final UserDB users;
    private final TourDB tours;

    public BookingDB() {
        users = new UserDB();
        tours = new TourDB();
    }

    /**
     * Sends a select query to the database.
     * @return the result from the database
     */
    public List<Pair<DayTour,User>> select() throws SQLException {
        List<Pair<DayTour,User>> list = new ArrayList<>();
        String str = "SELECT * FROM BookingsDB;";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            tours.connect();
            DayTour[] dayTour = tours.select(rs.getString("daytour"));
            tours.disconnect();
            users.connect();
            User user = users.select(rs.getString("userEmail"), rs.getString("userPassword"));
            users.disconnect();
            list.add(new Pair<>(dayTour[0], user));
        }
        return list;
    }

    /**
     * Sends a select query to the database.
     * @return the result from the database
     */
    public List<DayTour> selectByUser(User user) throws SQLException {
        List<DayTour> list = new ArrayList<>();
        String str = "SELECT * FROM BookingsDB WHERE userEmail = \""+user.getEmailAddress()+"\" AND userPassword = \""+user.getPassword()+"\";";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            tours.connect();
            DayTour[] dayTour = tours.select(rs.getString("daytour"));
            tours.disconnect();
            list.add(dayTour[0]);
        }
        return list;
    }

    /**
     * Sends an insert query to the database.
     */
    public void insert(DayTour dayTour, User user) throws SQLException {
        String str = "INSERT INTO BookingsDB VALUES (?,?,?);";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.setString(1,dayTour.getName());
        ps.setString(2,user.getEmailAddress());
        ps.setString(3,user.getPassword());
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Sends a delete query to the database.
     */
    public void delete(DayTour dayTour, User user) throws SQLException {
        String str = "DELETE FROM BookingsDB WHERE daytour = ? AND userEmail = ? AND userPassword = ?;";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.setString(1,dayTour.getName());
        ps.setString(2,user.getEmailAddress());
        ps.setString(3,user.getPassword());
        ps.executeUpdate();
        ps.close();
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        s = conn.createStatement();
    }

    public void disconnect() throws SQLException {
        conn.close();
        s.close();
    }
}
