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
    private DayTour dayTourTest;

    @Before
    public void setUp() {
        tours = new TourDB();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        f = f.withLocale(Locale.ENGLISH);
        dayTourTest = new DayTour("Northern Lights Super Jeep","Chase the Auroras in a comfort Jeep from Reykjavik", LocalDate.parse("2022/04/23",f),3,16000, 0.0F);
    }
    @Test
    public void testFindOne() throws ParseException {
        String searchQuery = "Lights";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertEquals(dayTourTest, testSearch[0]);
    }
    @Test
    public void testFindCaps() throws ParseException {
        String searchQuery = "LIGHTS";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertEquals(dayTourTest, testSearch[0]);
    }
    @Test
    public void testNotFind() {
        String searchQuery = "idk";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertEquals(0,testSearch.length);
    }
    @Test
    public void testFindMultiple() {
        String searchQuery = "Hik";
        DayTour[] testSearch = ToursController.search(searchQuery,tours);
        assertNotNull(testSearch);
        assertTrue(testSearch.length > 1);
    }
}
