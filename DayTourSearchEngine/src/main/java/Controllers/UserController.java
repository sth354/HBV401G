package Controllers;

import Databases.UserDB;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements Initializable {
    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPw;

    @FXML
    private Label error;

    private UserDB users;
    private static final String OK = "Login";
    private static final String CANCEL = "Cancel";
    private Dialog<ButtonType> dialogLogin;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType HTYPE = new ButtonType(CANCEL,
            ButtonBar.ButtonData.CANCEL_CLOSE);


    public User[] getUsers() {
        return null;
    }

    public void register() {
        
    }

    public User login() throws SQLException, ParseException {
        Optional<ButtonType> out = dialogLogin.showAndWait();
        if (out.isPresent() && (out.get() //login
                .getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
            Matcher m = pattern.matcher(loginEmail.getText());
            if (m.matches() && loginPw.getText().length() >= 1) {
                User loginUser = users.select(loginEmail.getText(), loginPw.getText());
                if (loginUser != null) {
                    error.setText("");
                    loginEmail.setText("");
                    loginPw.setText("");
                    return loginUser;
                }
                else {
                    error.setText("User not found");
                    return login();
                }
            }
            else {
                error.setText("Email is wrong");
                return login();
            }
        }
        error.setText("");
        loginEmail.setText("");
        loginPw.setText("");
        return null;
    }

    public void grantAuth(User user) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            users = new UserDB();
            dialogLogin = createDialogLogin();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Dialog<ButtonType> createDialogLogin() {
        DialogPane p = new DialogPane();
        loginPane.setVisible(true);

        p.setContent(loginPane);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        ButtonType ok = doneCancelButtons(d);

        loginRule(p, ok);

        return d;
    }

    private void head(Dialog<ButtonType> d) {
        d.setHeaderText("Login:");
        d.setTitle("Login");
    }

    private ButtonType doneCancelButtons(Dialog<ButtonType> d) {
        d.getDialogPane().getButtonTypes().add(BTYPE);
        d.getDialogPane().getButtonTypes().add(HTYPE);
        return BTYPE;
    }

    private void loginRule(DialogPane p, ButtonType ok) {
        final Node loginButton = p.lookupButton(ok);
        loginButton.disableProperty()
                .bind(loginEmail.textProperty().isEmpty()
                    .or(loginPw.textProperty().isEmpty()));
    }
}