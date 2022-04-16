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
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        f = f.withLocale(Locale.ENGLISH);
        dayTourTest = new DayTour("TestTour","tourtest", LocalDate.parse("2023/01/20",f),0,0, 0.0F);
        userTest = new User("testUser","test@email.com","test",false);
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