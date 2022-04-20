package databaseTests;

import Databases.BookingDB;
import Model.DayTour;
import Model.User;
import org.junit.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.*;

public class bookingTest {
    private BookingDB bookings;
    private DayTour dayTourTest;
    private User userTest;

    @Before
    public void setUp() {
        bookings = new BookingDB();
        try {
            bookings.connect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        f = f.withLocale(Locale.ENGLISH);
        dayTourTest = new DayTour("Northern Lights Super Jeep","Chase the Auroras in a comfort Jeep from Reykjavik", LocalDate.parse("2022/04/23",f),3,16000, 0.0F);
        userTest = new User("testUser","test@email.com","test",false);
    }

    @After
    public void cleanUp() {
        try {
            bookings.delete(dayTourTest, userTest);
        }
        catch (SQLException ignored) {}
        try {
            bookings.disconnect();
        }
        catch (SQLException ignored) {}
    }

    @Test
    public void addBooking() throws SQLException {
        bookings.insert(dayTourTest,userTest);
        assertEquals(bookings.selectByUser(userTest).get(0),dayTourTest);
    }

    @Test
    public void deleteBooking() throws SQLException {
        bookings.delete(dayTourTest, userTest);
    }
}