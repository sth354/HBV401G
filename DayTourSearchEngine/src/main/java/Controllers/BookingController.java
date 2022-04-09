package Controllers;

import Databases.BookingDB;
import Model.DayTour;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    private BookingDB bookings;

    @FXML
    private AnchorPane fxDialog;
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

    private static final String OK = "Buy Tour";
    private static final String CANCEL = "Cancel";

    private Dialog<ButtonType> dialogBooking;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType HTYPE = new ButtonType(CANCEL,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    public void makeBooking(DayTour dayTour, User user) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogBooking = createDialog();
        try {
            bookings = new BookingDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dialog<ButtonType> createDialog() {
        DialogPane p = new DialogPane();
        fxDialog.setVisible(true);

        p.setContent(fxDialog);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        doneCancelButtons(d);

        return d;
    }

    private void head(Dialog<ButtonType> d) {
        d.setHeaderText("Make booking:");
        d.setTitle("Booking");
    }

    private void doneCancelButtons(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
        d.getDialogPane().getButtonTypes().add(HTYPE);
    }
}