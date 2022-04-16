package Databases;

import Model.DayTour;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourDB {
    private Connection conn;
    private Statement s;

    public TourDB() {}

    /**
     * Sends an select query to the database.
     * @return the result from the database
     */
    public DayTour[] select(String searchQuery) throws SQLException {
        List<DayTour> out = new ArrayList<>();

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

    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:..\\databases\\tours.db");
        s = conn.createStatement();
    }

    public void disconnect() throws SQLException {
        conn.close();
        s.close();
    }
}
