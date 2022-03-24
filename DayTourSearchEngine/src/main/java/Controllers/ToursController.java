package Controllers;

import Databases.TourDBMock;
import Model.DayTour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    private TourDBMock tours;

    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    public DayTour onSearchButtonClick() {
        String searchQuery = searchBar.getText();
        DayTour tour = tours.select(searchQuery);
        return tour;
    }

    public void search(String searchQuery) {
        searchBar.setText(searchQuery);
    }

    public String getLabel() {
        return searchLabel.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tours = new TourDBMock();
        searchBar.requestFocus();
        searchBar.setText("");
    }
}