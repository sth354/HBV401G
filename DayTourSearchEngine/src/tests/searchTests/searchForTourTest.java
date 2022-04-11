package searchTests;

import Controllers.ToursController;
import Databases.TourDB;
import Model.DayTour;
import org.junit.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.*;

public class searchForTourTest {
    private TourDB tours;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        tours = new TourDB();
    }
    @Test
    public void testFindOne() throws ParseException {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        f = f.withLocale(Locale.ENGLISH);
        DayTour tour = new DayTour("Tour1","tourtest", LocalDate.parse("2022/03/31",f),120,5000, 0.0F);
        String searchQuery = "Tour1";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertEquals(tour, testSearch[0]);
    }
    @Test
    public void testFindCaps() throws ParseException {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        f = f.withLocale(Locale.ENGLISH);
        DayTour tour = new DayTour("Tour2","tourtest",LocalDate.parse("2022/04/01",f),120,5000, 0.0F);
        String searchQuery = "TOUR2";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertEquals(tour, testSearch[0]);
    }
    @Test
    public void testNotFind() {
        String searchQuery = "Tour4";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertEquals(0,testSearch.length);
    }
    @Test
    public void testFindMultiple() {
        String searchQuery = "Tour";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertTrue(testSearch.length > 1);
    }
}
