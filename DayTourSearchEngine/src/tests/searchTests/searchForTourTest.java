package searchTests;

import Controllers.ToursController;
import Model.DayTour;
import org.junit.*;
import static org.junit.Assert.*;

public class searchForTourTest {
    private ToursController tc;
    private DayTour tour;

    @Before
    public void setUp() {
        tc = new ToursController();
        tc.initialize(null,null);
        tour = new DayTour("Tour1");
    }
    @After
    public void tearDown() { //oþarfi
        tc = null;
        tour = null;
    }
    @Test
    public void testName() {
        String test1 = "1";

        DayTour searchedTour = tc.search(test1);

        assertEquals(tour.getName(),searchedTour.getName());
    }
}
