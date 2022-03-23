package Databases;

import Model.DayTour;

public class TourDBMock implements TourDB {
    private DayTour[] tours;

    public TourDBMock() {

    }

    @Override
    public DayTour select(String searchQuery) {
        return null;
    }
}
