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
        tc.initialize(null,null);
        tc.search(test1);
        tc.onSearchButtonClick();

        assertEquals(tour.getName(),tc.getLabel());
    }
}
