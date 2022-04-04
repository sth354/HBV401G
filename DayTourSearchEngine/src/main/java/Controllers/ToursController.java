package Controllers;

import Databases.TourDB;
import Model.DayTour;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    private TourDB tours;

    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    private Pane fxDialog;

    @FXML
    private ListView<DayTour> resultList;

    private static final String OK = "Done";

    private BookingController bc;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);

    @FXML
    public void onSearchButtonClick() {
        DayTour[] searchResult = search(searchBar.getText());
        try {
            if (searchResult.length == 0) {
                searchLabel.setText("Can't find tour");
                searchLabel.setTextFill(Color.RED);
            } else {
                searchLabel.setText("");

                Dialog<ButtonType> dialogResult = createDialog();
                resultList.setItems(FXCollections.observableList(Arrays.asList(searchResult)));
                dialogResult.showAndWait();
                resultList.setItems(null);
            }
        }
        catch (Exception ignored) {}
    }

    @FXML
    public void onEnterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onSearchButtonClick();
        }
    }

    @FXML
    public void onBuyButtonClick() {
       try {
           DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
           bc.makeBooking(selectedTour);
       }
       catch (NullPointerException ignored) {}
    }

    public DayTour[] search(String searchQuery) {
        if (searchQuery.equals("")) {
            searchLabel.setText("You have to type something...");
            searchLabel.setTextFill(Color.RED);
            return null;
        }
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
            bc = loadDialog();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private BookingController loadDialog() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("booking-view.fxml"));
        dLoader.load();
        return dLoader.getController();
    }

    private Dialog<ButtonType> createDialog() {
        DialogPane p = new DialogPane();

        fxDialog.setVisible(true);

        p.setContent(fxDialog);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        doneButton(d);

        return d;
    }

    private void head(Dialog<ButtonType> d) {
        d.setHeaderText("Search result:");
        d.setTitle("Search");
    }

    private void doneButton(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
    }
}