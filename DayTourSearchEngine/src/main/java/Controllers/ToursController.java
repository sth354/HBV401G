package Controllers;

import Databases.TourDB;
import Model.DayTour;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ToursController {
    private TourDB tours;

    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    public ToursController(TourDB tours) {
        this.tours = tours;
    }

    @FXML
    public void onSearchButtonClick() {
        searchLabel.setText("Yay!");
    }

    public DayTour[] search(String searchQuery) {
        try {
            return tours.select(searchQuery);
        }
        catch (Exception e) {
            return null;
        }
    }
}