package Controllers;

import Databases.TourDB;
import Databases.TourDBMock;
import Model.DayTour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    private TourDB tours;

    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    public void onSearchButtonClick() {
        searchLabel.setText("");
    }

    public DayTour[] search(String searchQuery) {
        searchQuery = searchQuery.toLowerCase();
        try {
            return tours.select(searchQuery);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tours = new TourDBMock();
    }
}