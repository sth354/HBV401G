package Controllers;

import Databases.BookingDB;
import Model.DayTour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    private BookingDB bookings;

    @FXML
    private AnchorPane fxDialog;
    @FXML
    private TextField fxCard;
    @FXML
    private Label fxName;
    @FXML
    private Label fxDate;
    @FXML
    private Label fxLength;
    @FXML
    private Label fxPrice;

    private static final String OK = "Buy Tour";
    private static final String CANCEL = "Cancel";

    private Dialog<ButtonType> dialogBooking;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType HTYPE = new ButtonType(CANCEL,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    public void makeBooking(DayTour dayTour) {
        fxName.setText(dayTour.getName());
        fxDate.setText(dayTour.getDate().toString());
        fxLength.setText(dayTour.getLength()+"");
        fxPrice.setText(dayTour.getPrice()+"");

        dialogBooking.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dialogBooking = createDialog();
    }

    private Dialog<ButtonType> createDialog() {
        DialogPane p = new DialogPane();
        fxDialog.setVisible(true);

        p.setContent(fxDialog);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        ButtonType ok = doneCancelButtons(d);

        buyRule(p, ok);

        return d;
    }

    private void head(Dialog<ButtonType> d) {
        d.setHeaderText("Make booking:");
        d.setTitle("Booking");
    }

    private ButtonType doneCancelButtons(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
        d.getDialogPane().getButtonTypes().add(HTYPE);
        return BTYPE;
    }

    private void buyRule(DialogPane p, ButtonType ok) {
        final Node buyButton = p.lookupButton(ok);
        buyButton.disableProperty()
                .bind(fxCard.textProperty().isEmpty());
    }
}