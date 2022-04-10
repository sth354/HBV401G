package Databases;

import Model.DayTour;
import Model.User;
import javafx.util.Pair;


import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BookingDB {
    private final Connection conn;

    private UserDB users;
    private TourDB tours;

    public BookingDB() throws SQLException, ClassNotFoundException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        users = new UserDB();
        tours = new TourDB();
    }

    public List<Pair<DayTour,User>> select() throws SQLException, ParseException {
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

    public void insert(DayTour dayTour, User user) throws SQLException {
        Statement s = conn.createStatement();
        String str = "INSERT INTO BookingsDB VALUES (\""+dayTour.getName()+"\",\""+user.getName()+"\",\""+user.getPassword()+"\");";
        s.executeUpdate(str);
    }
}
