package Controllers;

import Databases.TourDB;
import Model.DayTour;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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


    public static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);

    @FXML
    public void onSearchButtonClick() throws IOException {
        DayTour[] searchResult = search(searchBar.getText());
        if (searchResult.length == 0) {
            searchLabel.setText("Can't find tour");
            searchLabel.setTextFill(Color.RED);
        }
        else {
            searchLabel.setText("");

            Dialog<ButtonType> dialogResult = createDialog();
            resultList.setItems(FXCollections.observableList(Arrays.asList(searchResult)));
            dialogResult.showAndWait();
            resultList.setItems(null);
        }
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

    private Dialog<ButtonType> createDialog() {
        DialogPane p = new DialogPane();
        fxDialog.setVisible(true);

        p.setContent(fxDialog);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        doneButton(d);

        return d;
    }

    private void doneButton(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
    }

}