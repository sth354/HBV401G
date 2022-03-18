package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ToursController {
    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    protected void onSearchButtonClick() {
        searchLabel.setText("Searched!");
    }
}