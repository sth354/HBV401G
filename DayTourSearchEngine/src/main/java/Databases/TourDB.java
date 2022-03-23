package Databases;

import Model.DayTour;

public interface TourDB {
    public DayTour select(String searchQuery);
}
