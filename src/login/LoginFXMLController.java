package login;

import application.MainFXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginFXMLController {

    private boolean instantLogin = false;

    @FXML
    private StackPane root;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonLoginPopupButton;

    @FXML
    private HBox hboxpopup;

    @FXML
    private VBox vboxLogin;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private void handleButtonLogin(ActionEvent event) {

        boolean correctLogin = LoginData.getUsers().stream().anyMatch(user -> {
            if (textFieldUsername.getText().equals(user.getUsername()) && passwordFieldPassword.getText().equals(user.getPassword())) {
                LoginData.setLoggedInUser(user);
                return true;
            }
            return false;
        });

        if (instantLogin || correctLogin) {
            MainFXMLController.setMainFXMLChild("/dashboard/dashboard.fxml");
        } else {
            vboxLogin.setDisable(true);
            passwordFieldPassword.clear();
            hboxpopup.setVisible(true);
        }
    }

    @FXML
    private void handleButtonLoginPopup(ActionEvent event) {
        vboxLogin.setDisable(false);
        hboxpopup.setVisible(false);
    }
}
