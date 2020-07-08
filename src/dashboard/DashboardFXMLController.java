package dashboard;

import dashboard.dialogs.scannedPerson.ScannedPersonDialog;
import dashboard.dialogs.scannedTransponder.ScannedTransponderDialog;
import dashboard.transponders.TranspondersFXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import login.LoginData;
import records.Person;
import records.Transponder;
import records.User;
import records.data.PersonData;
import records.data.TransponderData;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DashboardFXMLController {

    @FXML
    private StackPane root;

    @FXML
    private StackPane stackPaneContent;

    @FXML
    private VBox vBoxNavigator;

    @FXML
    private VBox vBoxNavigatorComplete;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private Label labelName;

    private static StackPane staticStackPaneContent;

    private Random random = new Random();

    public static void setDashboardFXMLChild(String path) {
        try {
            staticStackPaneContent.getChildren().clear();
            staticStackPaneContent.getChildren().add(FXMLLoader.load(DashboardFXMLController.class.getResource(path)));
        } catch (IOException e) {
            Logger.getLogger(DashboardFXMLController.class.getSimpleName()).warning("Exception while loading FXML source: " + e);
        }
    }

    public static void setDashboardMessage(String message) {
        staticStackPaneContent.getChildren().clear();
        Label label = new Label(message);
        label.setFont(Font.font(36));
        staticStackPaneContent.getChildren().add(label);
    }

    @FXML
    private void initialize() {
        staticStackPaneContent = stackPaneContent;

        User loggedInUser = LoginData.getLoggedInUser();
        textFieldUsername.setText(loggedInUser.getUsername());
        labelName.setText(loggedInUser.getPerson().toString());
        switchNavigator();

        root.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
                simulateMultiCaScan();
            }
            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                simulateTransponderScan();
            }
        });
    }

    @FXML
    private void handleButtonNavigator(ActionEvent event) {
        switchNavigator();
    }

    @FXML
    private void handleButtonBack(ActionEvent event) {
    }

    @FXML
    private void handleButtonPersons(ActionEvent event) {
        setDashboardFXMLChild("/dashboard/persons/persons.fxml");
    }

    @FXML
    private void handleButtonRooms(ActionEvent event) {
        setDashboardFXMLChild("/dashboard/rooms/rooms.fxml");
    }

    @FXML
    private void handleButtonTransponders(ActionEvent event) {
        TranspondersFXMLController.CHOOSE_MODE_PERSON = null;
        setDashboardFXMLChild("/dashboard/transponders/transponders.fxml");
    }

    private void switchNavigator() {
        if (vBoxNavigatorComplete.getChildren().contains(vBoxNavigator)) {
            vBoxNavigatorComplete.getChildren().remove(vBoxNavigator);
            vBoxNavigatorComplete.setBorder(null);
        } else {
            vBoxNavigatorComplete.getChildren().add(vBoxNavigator);
            vBoxNavigatorComplete.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    private void simulateMultiCaScan() {
        System.out.println("Scanning MultiCa");
        Person randomPerson = PersonData.getPersons().get(random.nextInt(PersonData.getPersons().size()));

        ScannedPersonDialog personDialog = new ScannedPersonDialog(randomPerson, null);
        personDialog.showAndWait();
    }

    private void simulateTransponderScan() {
        System.out.println("Scanning Transponder");
        List<Transponder> lendTransponders = TransponderData.getTransponders().stream()
                .filter(transponder -> !transponder.availableProperty().getValue())
                .collect(Collectors.toList());


        if (!lendTransponders.isEmpty()) {
            Transponder randomTransponder = lendTransponders.get(random.nextInt(lendTransponders.size()));

            ScannedTransponderDialog transponderDialog = new ScannedTransponderDialog(randomTransponder);
            transponderDialog.showAndWait();
        }
    }
}
