package Controllers;

import Databases.TourDB;
import Model.DayTour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
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
        try {
            return tours.select(searchQuery);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tours = new TourDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}