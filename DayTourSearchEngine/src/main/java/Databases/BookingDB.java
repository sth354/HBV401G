package Databases;

import Model.DayTour;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookingDB {
    Connection conn;

    public BookingDB() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
    }

    public void insert(DayTour dayTour, User user) {

    }
}
