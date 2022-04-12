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
    private AnchorPane fxDialog;
    @FXML
    private ListView<DayTour> resultList;
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

    private static final String OK = "Done";
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);

    private static TourDB tours;
    private BookingController bc;
    private UserController uc;
    private User loggedInUser;

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

    public void onEnterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            onSearchButtonClick();
        }
    }

    public void onBuyButtonClick() {
       try {
           DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
           bc.makeBooking(selectedTour,loggedInUser);
       }
       catch (NullPointerException | SQLException ignored) {}
    }

    public void onLoginInClick() throws SQLException, ParseException {
        if (loginButton.getText().equals("Log In")) {
            User user = uc.login();

            if (user != null) {
                fxUserName.setText(user.toString());
                loggedIn.setVisible(true);
                loginButton.setText("Log Out");
                loggedInUser = user;
                buyMessage.setVisible(false);
                buyTour.setDisable(false);
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

    public void onRegisterClick() throws SQLException, ParseException {
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

    public void onViewButtonClick() {
        try {
            DayTour selectedTour = resultList.getSelectionModel().getSelectedItem();
            bc.viewTour(selectedTour);
        }
        catch (NullPointerException ignored) {}
    }

    public void getAllBookings() throws SQLException, ParseException {
        bc.viewBookings(true,null);
    }

    public void getMyBookings() throws SQLException, ParseException {
        bc.viewBookings(false,loggedInUser);
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
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getDate);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    public void sortByLength() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getLength);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    public void sortByPrice() {
        ObservableList<DayTour> list = resultList.getItems();
        Comparator<DayTour> c = Comparator.comparing(DayTour::getPrice);
        FXCollections.sort(list,c);
        resultList.setItems(list);
    }

    public static DayTour[] search(String searchQuery, TourDB tours1) {
        try {
            if (tours1 == null) {
                return tours.select(searchQuery);
            }
            else {
                return tours1.select(searchQuery);
            }
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
            searchButton.requestFocus();
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