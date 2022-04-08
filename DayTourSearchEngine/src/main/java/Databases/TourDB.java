package Databases;

import Model.DayTour;

import java.sql.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourDB {
    Connection conn;

    public TourDB() throws SQLException, ClassNotFoundException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
    }

    public DayTour[] select(String searchQuery) throws SQLException, ParseException {
        List<DayTour> out = new ArrayList<DayTour>();

        Statement s = conn.createStatement();
        String str = "SELECT * FROM DayToursDB WHERE name LIKE \"%"+searchQuery+"%\";";
        ResultSet rs = s.executeQuery(str);

        while (rs.next()) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            f = f.withLocale(Locale.ENGLISH);
            DayTour outD = new DayTour(rs.getString("name"),rs.getString("description"),LocalDate.parse(rs.getString("date"),f),rs.getInt("length"),rs.getInt("price"),rs.getFloat("averageRating"));
            out.add(outD);
        }

        return out.toArray(new DayTour[out.size()]);
    }
}
