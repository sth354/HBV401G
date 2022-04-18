package Controllers;

import Databases.TourDB;
import Model.DayTour;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ToursController implements Initializable {
    //interface variables
    @FXML
    private TextField searchBar;
    @FXML
    private AnchorPane fxDialog;
    @FXML
    private ListView<DayTour> resultList;
    @FXML
    private Label searchLabel;
    @FXML
    private Label fxUserName;
    @FXML
    private Label loggedIn;
    @FXML
    private Label buyMessage;
    @FXML
    private Button loginButton;
    @FXML
    private Button buyTour;
    @FXML
    private Button getBookingsButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button getMyBookingsButton;

    //constants
    private static final String OK = "Done";
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);

    //data variables
    private static TourDB tours;
    private BookingController bc;
    private UserController uc;
    private User loggedInUser;

    /**
     * Handler that sends the search result to the database
     * and displays the result in a dialog.
     */
    public void onSearchButtonClick() {
        DayTour[] searchResult = search(searchBar.getText(),null);
        try {
            assert searchResult != null;
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

    /**
     * Handler that calls the search handler if the user presses
     * the ENTER key.
     */
    public void onEnterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onSearchButtonClick();
        }
    }

    /**
     * Handler that calls the makeBooking method in BookingController.
     */
    public void onBuyButtonClick() {
       try {
           DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
           bc.makeBooking(selectedTour,loggedInUser);
       }
       catch (NullPointerException ignored) {}
    }

    /**
     * Handler that calls the login method in UserController.
     */
    public void onLoginInClick() {
        if (loginButton.getText().equals("Log In")) {
            User user = uc.login();
            if (user != null) {
                fxUserName.setText(user.toString());
                loggedIn.setVisible(true);
                loginButton.setText("Log Out");
                loggedInUser = user;
                buyMessage.setVisible(false);
                buyTour.setDisable(false);
                registerButton.setVisible(false);
                getMyBookingsButton.setDisable(false);
                if (loggedInUser.isModerator()) {
                    getBookingsButton.setVisible(true);
                }
            }
        }
        else {
            buyMessage.setVisible(true);
            buyTour.setDisable(true);
            fxUserName.setText("");
            loggedIn.setVisible(false);
            registerButton.setVisible(true);
            getMyBookingsButton.setDisable(true);
            loginButton.setText("Log In");
            loggedInUser = null;
            getBookingsButton.setVisible(false);
        }
    }

    /**
     * Handler that calls the login method in UserController.
     */
    public void onRegisterClick() {
        User user = uc.register();

        if (user != null) {
            fxUserName.setText(user.toString());
            loggedIn.setVisible(true);
            loginButton.setText("Log Out");
            loggedInUser = user;
            buyMessage.setVisible(false);
            registerButton.setVisible(false);
            buyTour.setDisable(false);
            if (loggedInUser.isModerator()) {
                getBookingsButton.setVisible(true);
            }
        }
    }

    /**
     * Handler that calls the viewTour method in BookingController.
     */
    public void onViewButtonClick() {
        try {
            DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
            bc.viewTour(selectedTour);
        }
        catch (NullPointerException ignored) {}
    }

    /**
     * Handler that calls the viewBookings method in BookingController.
     */
    public void getAllBookings() {
        bc.viewBookings(true,null);
    }

    /**
     * Handler that calls the viewBookings method in BookingController.
     */
    public void getMyBookings() {
        bc.viewBookings(false,loggedInUser);
    }

    /**
     * Handler that sorts the search result in alphabetical order.
     */
    public void sortByAZ() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getName);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    /**
     * Handler that sorts the search result in reverse-alphabetical order.
     */
    public void sortByZA() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getName);
        c = c.reversed();
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    /**
     * Handler that sorts the search result by date.
     */
    public void sortByDate() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getDate);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    /**
     * Handler that sorts the search result by length.
     */
    public void sortByLength() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getLength);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    /**
     * Handler that sorts the search result by price.
     */
    public void sortByPrice() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getPrice);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    /**
     * Calls the select method in TourDB;
     * @param searchQuery the search query from the user
     * @param tours1 the tours database class
     * @return the DayTour objects found with the search query
     */
    public static DayTour[] search(String searchQuery, TourDB tours1) {
        try {
            tours.connect();
            if (tours1 == null) {
                DayTour[] d = tours.select(searchQuery);
                tours.disconnect();
                return d;
            }
            else {
                return tours1.select(searchQuery);
            }
        }
        catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tours = new TourDB();
        searchButton.requestFocus();
        try {
            bc = loadDialogBooking();
            uc = loadDialogUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the fxml file for the BookingController.
     */
    private BookingController loadDialogBooking() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("booking-view.fxml"));
        dLoader.load();
        return dLoader.getController();
    }

    /**
     * Loads the fxml file for the UserController.
     */
    private UserController loadDialogUser() throws java.io.IOException {
        FXMLLoader dLoader = new FXMLLoader(getClass().getResource("log-in-page.fxml"));
        dLoader.load();
        return dLoader.getController();
    }

    /**
     * Creates the search result dialog.
     */
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

    /**
     * Creates the header for the search result dialog.
     */
    private void head(Dialog<ButtonType> d) {
        d.setHeaderText("Search result:");
        d.setTitle("Search");
    }

    /**
     * Creates the done button for the search result dialog.
     * @param d the dialog to add the button to
     */
    private void doneButton(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
    }
}