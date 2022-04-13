package Databases;

import Model.DayTour;
import Model.User;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private Connection conn;
    private final UserDB users;
    private final TourDB tours;

    public BookingDB() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        }
        catch (SQLException ignored) {}
        users = new UserDB();
        tours = new TourDB();
    }

    public List<Pair<DayTour,User>> select() throws SQLException {
        List<Pair<DayTour,User>> list = new ArrayList<>();
        Statement s = conn.createStatement();
        String str = "SELECT * FROM BookingsDB;";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            DayTour[] dayTour = tours.select(rs.getString("daytour"));
            User user = users.select(rs.getString("userEmail"), rs.getString("userPassword"));
            list.add(new Pair<>(dayTour[0], user));
        }
        return list;
    }

    public List<DayTour> selectByUser(User user) throws SQLException {
        List<DayTour> list = new ArrayList<>();
        Statement s = conn.createStatement();
        String str = "SELECT * FROM BookingsDB WHERE userEmail = \""+user.getEmailAddress()+"\" AND userPassword = \""+user.getPassword()+"\";";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            DayTour[] dayTour = tours.select(rs.getString("daytour"));
            list.add(dayTour[0]);
        }
        return list;
    }

    public void insert(DayTour dayTour, User user) throws SQLException {
        String str = "INSERT INTO BookingsDB VALUES (?,?,?);";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.setString(1,dayTour.getName());
        ps.setString(2,user.getEmailAddress());
        ps.setString(3,user.getPassword());
        System.out.println(ps);
        ps.executeUpdate();
    }

    public void delete(DayTour dayTour, User user) throws SQLException {
        String str = "DELETE FROM BookingsDB WHERE daytour = ? AND userEmail = ? AND userPassword = ?);";
        PreparedStatement ps = conn.prepareStatement(str);
        ps.setString(1,dayTour.getName());
        ps.setString(2,user.getEmailAddress());
        ps.setString(3,user.getPassword());
        ps.executeUpdate(str);
    }
}
