package searchTests;

import Controllers.ToursController;
import Databases.TourDBMock;
import Model.DayTour;
import org.junit.*;
import static org.junit.Assert.*;

public class searchForTourTest {
    private TourDBMock tourDBMock;
    private ToursController tc;

    @Before
    public void setUp() {
        tourDBMock = new TourDBMock();
        tc = new ToursController(tourDBMock);
    }
    @Test
    public void testFindOne() {
        DayTour tour = new DayTour("Tour1");
        String searchQuery = "our1";
        DayTour[] testSearch = tc.search(searchQuery);
        assertNotNull(testSearch);
        assertEquals(testSearch[0].getName(),tour.getName());
    }
    @Test
    public void testNotFind() {
        String searchQuery = "our4";
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
