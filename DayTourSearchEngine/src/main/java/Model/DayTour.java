package Model;

import java.time.LocalDate;

public class DayTour {
    private final String name;
    private final String description;
    private final LocalDate date;
    private final int length;
    private final int price;
    private final float averageRating;

    public DayTour(String name, String description, LocalDate date, int length, int price, float averageRating) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.length = length;
        this.price = price;
        this.averageRating = averageRating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getLength() {
        return length;
    }

    public int getPrice() {
        return price;
    }

    public float getAverageRating() {
        return averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTour dayTour = (DayTour) o;
        return length == dayTour.getLength() && price == dayTour.getPrice() && Float.compare(dayTour.getAverageRating(), averageRating) == 0 && name.equals(dayTour.getName()) && description.equals(dayTour.getDescription()) && date.equals(dayTour.getDate());
    }

    @Override
    public String toString() {
        return name + ", " + date.toString() + ", " + length + " hours, " + price + "kr";
    }
}
