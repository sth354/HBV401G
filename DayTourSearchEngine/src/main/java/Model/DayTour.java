package Model;

import java.util.Date;

public class DayTour {
    private String name;
    private String description;
    private Date date;
    private int length;
    //private Tag[] tags;
    private int price;
    private float averageRating;
    //private User[] authUsers;

    public DayTour(String name, String description, Date date, int length, /*Tag[] tags,*/ int price, float averageRating /*, User[] authUsers*/) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.length = length;
        //this.tags = tags;
        this.price = price;
        this.averageRating = averageRating;
        //this.authUsers = authUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /*
    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }
    */

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    /*
    public User[] getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(User[] authUsers) {
        this.authUsers = authUsers;
    }
    */

    public boolean equals(DayTour dayTour) {
        return this.getName().equals(dayTour.getName());
    }
}
