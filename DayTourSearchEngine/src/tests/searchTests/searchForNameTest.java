package searchTests;

import Controllers.ToursController;
import Model.DayTour;
import org.junit.*;
import static org.junit.Assert.*;

public class searchForNameTest {
    private ToursController tc;
    private DayTour tour;

    @Before
    public void setUp() {
        tc = new ToursController();
        tc.initialize(null,null);
        tour = new DayTour("Tour1");
    }
    @After
    public void tearDown() {
        tc = null;
        tour = null;
    }
    @Test
    public void testName() {
        String test1 = "our1";

        DayTour searchedTour = tc.search(test1);

        assertEquals(tour.getName(),searchedTour.getName());
    }
}
