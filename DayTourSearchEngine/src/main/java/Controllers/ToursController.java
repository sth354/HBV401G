package Controllers;

import Databases.TourDB;
import Model.DayTour;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    @FXML
    private TextField searchBar;

    @FXML
    private Label searchLabel;

    @FXML
    private Pane fxDialog;

    @FXML
    private ListView<DayTour> resultList;

    @FXML
    private Label fxUserName;

    @FXML
    private Label loggedIn;

    @FXML
    private Button loginButton;

    private static final String OK = "Done";

    private TourDB tours;
    private BookingController bc;
    private UserController uc;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private User loggedInUser;

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

    public void onEnterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onSearchButtonClick();
        }
    }

    public void onBuyButtonClick() {
       try {
           DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
           bc.makeBooking(selectedTour);
       }
       catch (NullPointerException ignored) {}
    }

    public void onLoginInClick() throws SQLException, ParseException {
        if (loginButton.getText().equals("Log In")) {
            User user = uc.login();

            if (user != null) {
                fxUserName.setText(user.getName());
                loggedIn.setVisible(true);
                loginButton.setText("Log Out");
                loggedInUser = user;
            }
        }
        else {
            fxUserName.setText("");
            loggedIn.setVisible(false);
            loginButton.setText("Log In");
            loggedInUser = null;
        }
    }

    public void sortByAZ() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getName);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    public void sortByZA() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getName);
        c = c.reversed();
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    public void sortByDate() {
    }

    public void sortByLength() {
    }

    public void sortByPrice() {
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
            bc = loadDialogBooking();
            uc = loadDialogUser();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private BookingController loadDialogBooking() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("booking-view.fxml"));
        dLoader.load();
        return dLoader.getController();
    }

    private UserController loadDialogUser() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("log-in-page.fxml"));
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