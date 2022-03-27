package searchTests;

import Controllers.ToursController;
import Databases.TourDBMock;
import Model.DayTour;
import org.junit.*;
import static org.junit.Assert.*;

public class searchForTourTest {
    private ToursController tc;

    @Before
    public void setUp() {
        TourDBMock tourDBMock = new TourDBMock();
        tc = new ToursController(tourDBMock);
    }
    @Test
    public void testFindOne() {
        DayTour tour = new DayTour("Tour1");
        String searchQuery = "Tour1";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNotNull(testSearch);
        assertTrue(tour.equals(testSearch[0]));
    }
    @Test
    public void testFindCaps() {
        DayTour tour = new DayTour("Tour2");
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
