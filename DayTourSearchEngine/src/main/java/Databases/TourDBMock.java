package Databases;

import Model.DayTour;

public class TourDBMock implements TourDB {
    private DayTour[] tours;

    public TourDBMock() {
        DayTour tour1 = new DayTour("Tour1");
        DayTour tour2 = new DayTour("Tour2");
        DayTour tour3 = new DayTour("Tour3");

        tours = new DayTour[] {tour1,tour2,tour3};
    }

    @Override
    public DayTour select(String searchQuery) {
        for (int i = 0;i < tours.length; i++) {
            int x = tours[i].getName().indexOf(searchQuery);
            if (x != -1) {
                return tours[i];
            }
        }
        return null;
    }
}
