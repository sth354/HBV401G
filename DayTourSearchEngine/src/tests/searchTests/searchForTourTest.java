package searchTests;

import Controllers.ToursController;
import Model.DayTour;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class searchForTourTest {
    private ToursController tc;

    @Before
    public void setUp() {
        tc = new ToursController();
        tc.initialize(null,null);
    }
    @Test
    public void testFindOne() throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        DayTour tour = new DayTour("Tour1","testtour",f.parse("2022/03/31"),120,500, 0.0F);
        String searchQuery = "Tour1";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNotNull(testSearch);
        assertTrue(tour.equals(testSearch[0]));
    }
    @Test
    public void testFindCaps() throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        DayTour tour = new DayTour("Tour2","testtour",f.parse("2022/04/01"),120,500, 0.0F);
        String searchQuery = "TOUR2";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNotNull(testSearch);
        assertTrue(tour.equals(testSearch[0]));
    }
    @Test
    public void testNotFind() {
        String searchQuery = "Tour4";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNull(testSearch);
    }
    @Test
    public void testFindMultiple() {
        String searchQuery = "Tour";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNotNull(testSearch);
        assertEquals(3,testSearch.length);
    }
}
