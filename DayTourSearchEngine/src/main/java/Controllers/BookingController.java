package Controllers;

import Databases.BookingDB;
import Model.DayTour;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    private BookingDB bookings;

    @FXML
    private AnchorPane fxDialog;
    @FXML
    private AnchorPane fxDialog1;
    @FXML
    private Label fxName;
    @FXML
    private Label fxDate;
    @FXML
    private Label fxLength;
    @FXML
    private Label fxPrice;
    @FXML
    private Label fxDescription;
    @FXML
    private ListView<DayTour> listTours;
    @FXML
    private ListView<User> listUsers;

    private static final String BUY = "Buy Tour";
    private static final String OK = "Done";
    private static final String CANCEL = "Cancel";

    private Dialog<ButtonType> dialogBooking;
    private static final ButtonType BTYPE = new ButtonType(BUY,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType BTYPE1 = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType HTYPE = new ButtonType(CANCEL,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    public void makeBooking(DayTour dayTour, User user) throws SQLException {
        dialogBooking = createDialog(0);
        fxName.setText(dayTour.getName());
        fxDate.setText(dayTour.getDate().toString());
        fxLength.setText(dayTour.getLength()+"min");
        fxPrice.setText(dayTour.getPrice()+"kr");
        fxDescription.setText(dayTour.getDescription());

        Optional<ButtonType> out = dialogBooking.showAndWait();
        if (out.isPresent() && (out.get() // buy
                .getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            bookings.insert(dayTour,user);
        }
    }

    public void viewBookings() throws SQLException, ParseException {
        dialogBooking = createDialog(1);
        List<Pair<DayTour,User>> list = bookings.select();
        List<DayTour> dayTours = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (Pair<DayTour,User> p : list) {
                dayTours.add(p.getKey());
                users.add(p.getValue());
        }
        listTours.setItems(FXCollections.observableList(dayTours));
        listUsers.setItems(FXCollections.observableList(users));
        dialogBooking.showAndWait();
    }

    public void viewTour(DayTour dayTour) {
        dialogBooking = createDialog(2);
        fxName.setText(dayTour.getName());
        fxDate.setText(dayTour.getDate().toString());
        fxLength.setText(dayTour.getLength()+"min");
        fxPrice.setText(dayTour.getPrice()+"kr");
        fxDescription.setText(dayTour.getDescription());

        dialogBooking.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            bookings = new BookingDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Dialog<ButtonType> createDialog(int i) {
        DialogPane p = new DialogPane();
        if (i == 1) {
            fxDialog.setVisible(false);
            fxDialog1.setVisible(true);

            p.setContent(fxDialog1);
        }
        else {
            fxDialog1.setVisible(false);
            fxDialog.setVisible(true);

            p.setContent(fxDialog);
        }

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d,i);

        doneCancelButtons(d,i);

        return d;
    }

    private void head(Dialog<ButtonType> d,int i) {
        if (i == 0) {
            d.setHeaderText("Make booking:");
            d.setTitle("Booking");
        }
        else if (i == 1){
            d.setHeaderText("Bookings:");
            d.setTitle("Bookings");
        }
        else {
            d.setHeaderText("Tour:");
            d.setTitle("View tours");
        }
    }

    private void doneCancelButtons(Dialog<ButtonType> d,int i) {
        if (i == 0) {
            d.getDialogPane().getButtonTypes().add(BTYPE);
            d.getDialogPane().getButtonTypes().add(HTYPE);
        }
        else {
            d.getDialogPane().getButtonTypes().add(BTYPE1);
        }
    }
}