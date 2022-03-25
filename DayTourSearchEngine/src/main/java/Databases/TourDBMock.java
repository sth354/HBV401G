package Databases;

import Model.DayTour;

import java.util.ArrayList;
import java.util.List;

public class TourDBMock implements TourDB {
    private DayTour[] tours;

    public TourDBMock() {
        DayTour tour1 = new DayTour("Tour1");
        DayTour tour2 = new DayTour("Tour2");
        DayTour tour3 = new DayTour("Tour3");

        tours = new DayTour[] {tour1,tour2,tour3};
    }

    @Override
    public DayTour[] select(String searchQuery) throws Exception {
        List<DayTour> out = new ArrayList<DayTour>();
        for (DayTour tour : tours) {
            int x = tour.getName().toLowerCase().indexOf(searchQuery);
            if (x != -1) {
                out.add(tour);
            }
        }
        if (out.size() == 0) throw new Exception();
        return out.toArray(new DayTour[out.size()]);
    }
}
