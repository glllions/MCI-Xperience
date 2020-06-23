package login;

import application.MainFXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class LoginFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private Button buttonLogin;


    @FXML
    private void handleButtonLogin(ActionEvent event) {
        MainFXMLController.setMainFXMLChild("/dashboard/dashboard.fxml");
    }
}
