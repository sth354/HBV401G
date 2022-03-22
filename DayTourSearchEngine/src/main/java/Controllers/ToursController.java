package Controllers;

import Model.DayTour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    private DayTour[] tours;

    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    protected void onSearchButtonClick() {
        searchLabel.setText("Searched!");
    }

    public DayTour search(String searchQuery) {
        for (int i = 0;i < tours.length; i++) {
            int x = tours[i].getName().indexOf(searchQuery);
            if (x != -1) {
                return tours[i];
            }
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DayTour tour1 = new DayTour("Tour1");
        DayTour tour2 = new DayTour("Tour2");
        DayTour tour3 = new DayTour("Tour3");

        tours = new DayTour[] {tour1,tour2,tour3};
    }
}