package Databases;

import Model.DayTour;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TourDB {
    Connection conn;

    public TourDB() throws SQLException, ClassNotFoundException {
        conn = DriverManager.getConnection("jdbc:sqlite:tours.db");
    }

    public DayTour[] select(String searchQuery) throws SQLException, ParseException {
        List<DayTour> out = new ArrayList<DayTour>();

        Statement s = conn.createStatement();
        String str = "SELECT * FROM DayToursDB WHERE name LIKE \"%"+searchQuery+"%\";";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
            DayTour outD = new DayTour(rs.getString("name"),rs.getString("description"),f.parse(rs.getString("date")),rs.getInt("length"),rs.getInt("price"),rs.getFloat("averageRating"));
            out.add(outD);
        }

        return out.toArray(new DayTour[out.size()]);
    }
}
