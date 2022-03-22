package Model;

import java.util.Date;

public class DayTour {
    private String name;
    private String description;
    private Date date;
    private int length;
    private Tag[] tags;
    private int price;
    private float averageRating;
    private User[] authUsers;

    public DayTour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
