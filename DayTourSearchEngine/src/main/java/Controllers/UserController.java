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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements Initializable {
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane registerPane;
    @FXML
    private TextField loginEmail;
    @FXML
    private TextField userName;
    @FXML
    private TextField registerEmail;
    @FXML
    private PasswordField loginPw;
    @FXML
    private PasswordField registerPw;
    @FXML
    private Label error;
    @FXML
    private Label error1;

    private static final String OK = "Login";
    private static final String CANCEL = "Cancel";
    private Dialog<ButtonType> dialog;
    private static final ButtonType BTYPE = new ButtonType(OK,
            ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType HTYPE = new ButtonType(CANCEL,
            ButtonBar.ButtonData.CANCEL_CLOSE);

    private UserDB users;

    public User register() throws SQLException {
        dialog = createDialogRegister();
        Optional<ButtonType> out = dialog.showAndWait();

        if (out.isPresent() && (out.get() //register
                .getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            User user = new User(userName.getText(),registerEmail.getText(),registerPw.getText(),false);
            if (checkUser(userName.getText(),registerEmail.getText(),registerPw.getText())) {
                error1.setText("");
                userName.setText("");
                registerEmail.setText("");
                registerPw.setText("");
                return user;
            }
            else {
                error1.setText("User not valid");
                return register();
            }
        }
        return null;
    }

    public User login() throws SQLException {
        dialog = createDialogLogin();
        Optional<ButtonType> out = dialog.showAndWait();
        if (out.isPresent() && (out.get() //login
                .getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            User user = users.select(loginEmail.getText(), loginPw.getText());
            if (checkUser(null,loginEmail.getText(),loginPw.getText())) {
                error.setText("");
                return user;
            }
            else {
                error.setText("User not found");
                return login();
            }
        }
        error.setText("");
        loginEmail.setText("");
        loginPw.setText("");
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            users = new UserDB();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUser(String name, String email,String password) throws SQLException {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = pattern.matcher(email);
        User user = users.select(email, password);
        if (!(name == null)) {
            if (users.checkName(name)) {
                return false;
            }
            else if (m.matches() && password.length() >= 1) {
                return user == null;
            }
        }
        if (m.matches() && password.length() >= 1) {
            return user != null;
        }
        return false;
    }

    private Dialog<ButtonType> createDialogLogin() {
        DialogPane p = new DialogPane();
        loginPane.setVisible(true);
        registerPane.setVisible(false);

        p.setContent(loginPane);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        ButtonType ok = doneCancelButtons(d);

        loginRule(p, ok);

        return d;
    }

    private Dialog<ButtonType> createDialogRegister() {
        DialogPane p = new DialogPane();
        loginPane.setVisible(false);
        registerPane.setVisible(true);

        p.setContent(registerPane);

        Dialog<ButtonType> d = new Dialog<>();

        d.setDialogPane(p);

        head(d);

        ButtonType ok = doneCancelButtons(d);

        registerRule(p, ok);

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

    private void registerRule(DialogPane p, ButtonType ok) {
        final Node loginButton = p.lookupButton(ok);
        loginButton.disableProperty()
                .bind(userName.textProperty().isEmpty()
                    .or(registerEmail.textProperty().isEmpty()
                        .or(registerPw.textProperty().isEmpty())));
    }

    private void loginRule(DialogPane p, ButtonType ok) {
        final Node loginButton = p.lookupButton(ok);
        loginButton.disableProperty()
                .bind(loginEmail.textProperty().isEmpty()
                        .or(loginPw.textProperty().isEmpty()));
    }
}